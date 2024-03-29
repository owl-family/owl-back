package com.project.owlback.user.service;

import com.project.owlback.user.dto.SessionUser;
import com.project.owlback.user.dto.req.Tokens;
import com.project.owlback.util.Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.owlback.user.api.jwt.JwtTokenProvider;
import com.project.owlback.user.dto.req.Login;
import com.project.owlback.user.dto.res.TokenInfo;
import com.project.owlback.user.repository.UserRepository;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, String> redisTemplate;

    public Optional<TokenInfo> login(Login login) {		// email, password 기반으로 Authentication 객체 생성
        if (userRepository.findByEmail(login.getEmail()).orElse(null) == null) {
            // 해당하는 유저가 존재하지 않음
            return Optional.empty();
        }

        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 비밀번호 체크가 이루어지는 부분
        // authenticate 메서드가 실행될 때 service에서 만든 loadUserByUsername 메서드가 실행
        // 내부 retrieveUser 메서드에서 loadedUser 객체를 가져오기 위해서는 loadUserByUsername 메서드를 "직접" 구현해야한다.(serviceImpl)
        // password가 일치하면 Authentication의 authenticated 값이 true가 되고 인증 완료된 Authentication 객체가 됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증번호 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, null);

        // RefreshToken Redis 저장(Expiration 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue() // set the value and expiration timeout for key
                .set("RT:"+authentication.getName(), // key
                        tokenInfo.getRefreshToken(),  // value
                        tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS); // expiration timeout

        // 로그인 성공
        return Optional.of(tokenInfo);
    }

    @Override
    public Optional<TokenInfo> reissue(Tokens reissue) {
        // refresh token이 유효한지 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            // refresh token 정보가 유효하지 않음
            return Optional.empty();
        }

        // access token에서 Authentication 객체를 가지고 옴
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // userId(authentication.getName())을 가지고
        // redis에 저장된 refresh token을 가지고 옴(.get("RT:" + authentication.getName()))
        String refreshToken = (String)redisTemplate.opsForValue()
                .get("RT:" + authentication.getName());

        // 로그아웃되어 redis 에 refresh token 이 존재하지 않는 경우 처리
        // refresh token이 존재하는지 확인
        if(ObjectUtils.isEmpty(refreshToken)) {
            return Optional.empty();
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) { // 입력받은 refresh token값과 비교
            // refresh token 정보가 일치하지 않음
            return Optional.empty();
        }

        // access token이 만료됐으면
        // 새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, refreshToken);

        // redis에 새로 생성된 refresh token을 저장합니다.
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(),
                        tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        // token 정보 갱신 완료
        return Optional.of(tokenInfo);
    }

    @Override
    public Boolean logout(String accessToken) {
        // access token 유효성 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return false;
        }

        // access token을 통해 Authentication 객체를 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // email(authentication.getName())을 가지고
        // redis에 저장된 refresh token을 가지고 옴(.get("RT:" + authentication.getName()))
        // refresh token이 존재하면 삭제!
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // refresh token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // access token 유효시간 가지고 와서 BlackList로 저장하기
        // key: access token, value: logout
        Long expirationTime = jwtTokenProvider.getExpirationTime(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expirationTime, TimeUnit.MILLISECONDS);

        return true;
    }

    private final HttpSession httpSession;

    @Override
    public ResponseEntity<?> socialLogin() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        httpSession.removeAttribute("user");

        if(user.getNickname() == null)
            return Response.makeResponse(HttpStatus.CREATED, "sinUp", 1, user);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(user);
        return Response.makeResponse(HttpStatus.CREATED, "로그인이 성공했습니다.", 1, tokenInfo);
    }

}
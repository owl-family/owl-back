package com.project.owlback.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.owlback.Response;
import com.project.owlback.user.api.jwt.JwtTokenProvider;
import com.project.owlback.user.dto.Login;
import com.project.owlback.user.dto.TokenInfo;
import com.project.owlback.user.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final Response response;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, String> redisTemplate;

    public ResponseEntity<?> login(Login login) {		// email, password 기반으로 Authentication 객체 생성
        if (userRepository.findByEmail(login.getEmail()).orElse(null) == null) {
            return response.fail("해당하는 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 비밀번호 체크가 이루어지는 부분
        // authenticate 메서드가 실행될 때 service에서 만든 loadUserByUsername 메서드가 실행
        // 내부 retrieveUser 메서드에서 loadedUser 객체를 가져오기 위해서는 loadUserByUsername 메서드를 "직접" 구현해야한다.(serviceImpl)
        // password가 일치하면 Authentication의 authenticated 값이 true가 되고 인증 완료된 Authentication 객체가 됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증번호 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 저장(Expiration 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue() // set the value and expiration timeout for key
                .set("RT:"+authentication.getName(), // key
                        tokenInfo.getRefreshToken(),  // value
                        tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS); // expiration timeout

        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }




}
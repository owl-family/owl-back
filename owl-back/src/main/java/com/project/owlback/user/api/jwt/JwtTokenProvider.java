package com.project.owlback.user.api.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.project.owlback.user.dto.SessionUser;
import com.project.owlback.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.owlback.user.dto.res.TokenInfo;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
public class JwtTokenProvider {
    private final UserRepository userRepository;

    private static final String BEARER_TYPE = "Bearer";
    // 유효시간이 짧은 Access Token을 발급하고, Access Token이 만료되었을 때 재발급을 위한 Refresh Token을 발급
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일

    private Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            UserRepository userRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
    }

    // Service에서 Authentication 객체 인증 후, 인증된 객체를 가지고 해당 메서드를 통해 AccessToken, RefreshToken 생성
    public TokenInfo generateToken(Authentication authentication, String refreshToken) {
        long now = (new Date()).getTime();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername(); // 저장된 user Id 가져오기

        // userId로 User 정보들 가져오기
        com.project.owlback.user.dto.User user = userRepository.findByUserId(username).get();

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = createAccessToken(authorities, user, accessTokenExpiresIn);

        // refresh token이 없을 때
        if(refreshToken == null) {
            // Refresh Token 생성
            refreshToken = createRefreshToken(now);
        }

        return TokenInfo.builder()
                .grantType(BEARER_TYPE) // JWT 혹은 OAuth에 대한 토큰을 사용
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    private String createAccessToken(String authorities, com.project.owlback.user.dto.User user, Date accessTokenExpiresIn) {
        return Jwts.builder()
                .setSubject(Long.toString(user.getUserId())) // 아이디
                .claim("nickName", user.getNickname())
                .claim("auth", authorities) // 권한
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshToken(long now) {
        return Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 정보를 검증하는 메서드
    // ExpirationTime도 같이 검증함
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token, e");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (MalformedJwtException | SignatureException e) {
            log.info("Invalid JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Claims string is empty", e);
        }

        return false;
    }

    // JWT 토큰을 디코딩해서 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // 권한 정보가 있는지 확인
        if(claims.get("auth") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 복호화
    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getExpirationTime(String accessToken) {
        // access token 남은 유효시간 가져옴
        Date expirationTime = parseClaims(accessToken).getExpiration();

        // 현재 시간
        Long now = new Date().getTime();
        return expirationTime.getTime() - now;
    }

    public TokenInfo generateToken(SessionUser sessionUser) {
        long now = (new Date()).getTime();

        // userId로 User 정보들 가져오기
        com.project.owlback.user.dto.User user = userRepository.findByEmail(sessionUser.getEmail()).get();

        // 권한 가져오기
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = createAccessToken(authorities, user, accessTokenExpiresIn);

        // Refresh Token 생성
        String refreshToken = createRefreshToken(now);

        return TokenInfo.builder()
                .grantType(BEARER_TYPE) // JWT 혹은 OAuth에 대한 토큰을 사용
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }
}
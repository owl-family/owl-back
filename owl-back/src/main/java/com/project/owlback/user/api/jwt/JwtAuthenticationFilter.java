package com.project.owlback.user.api.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Request Header 에서 JWT 토큰 추출
        String accessToken = resolveToken((HttpServletRequest) request);

        // validateToken으로 토큰 유효성 검사
        // access token이 null이 아니고 유효하다면
        if(accessToken != null && jwtTokenProvider.validateToken(accessToken)){
            // Redis에 해당 access token logout 여부 확인
            String isLogout = (String)redisTemplate.opsForValue() // OpsForValue(): String 값에 대한 Redis 작업이다.
                    .get(accessToken); // get(key): token(key)값을 가져온다
            if (ObjectUtils.isEmpty(isLogout)) {
                // Authentication객체를 가지고 와서
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                // SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    // Request Header 에서 JWT 토큰 추출하는 메서드
    private String resolveToken(HttpServletRequest request){
        // HttpServletRequest객체에서 Header 이름이 "Authorization"인 헤더를 가져옴
        String token = request.getHeader("Authorization");

        // 토큰이 "Bearer"로 시작하는지 확인
        if (StringUtils.hasText(token) && token.startsWith(BEARER_TYPE)) {
            // "Bearer "를 잘라냄
            return token.substring(7);
        }

        return null;
    }
}
package com.project.owlback.user.api.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String BEARER_TYPE = "Bearer";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // validationToken으로 토큰 유효성 검사
        // token이 null이 아니고 유효하다면
        if(token != null && jwtTokenProvider.validationToken(token)){
            // Authentication객체를 가지고 와서
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
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

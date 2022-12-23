package com.project.owlback.config;

import com.project.owlback.user.api.jwt.JwtTokenProvider;
import com.project.owlback.user.api.jwt.JwtAuthenticationFilter;
import com.project.owlback.user.api.jwt.JwtTokenProvider;
import com.project.owlback.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 basic auth 인증을 사용하지 않음
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable() // rest api이므로 csrf 보안을 사용하지 않음
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/v3/api-docs/**", "/swagger*/**") // swagger
                        .permitAll()
                        .requestMatchers("/api/users/login/**", "api/users/reissue", "/oauth2/**", "/login/**") // login
                        .permitAll()
                        .requestMatchers("/api/users/logout") // logout
                        .hasAnyRole(new String[]{"ADMIN", "USER"}) // hasAnyRole이 String 앞에 "ROLE_"을 추가해줌
                        .requestMatchers("/api/**") // for test
                        .permitAll()
                )
                // JWT인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정
                // 여기서 Role과 access token을 검사하게됨
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
//                    .defaultSuccessUrl("http://localhost:8080/api/users/login/social", true)
//                    .failureUrl("http://localhost/login")
                    .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                    .userService(customOAuth2UserService); // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
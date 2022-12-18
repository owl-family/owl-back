package com.project.owlback.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {

    private String grantType; // 토큰 인증타입
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

}
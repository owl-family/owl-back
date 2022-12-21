package com.project.owlback.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class Tokens {
    private String accessToken;
    private String refreshToken;
}

package com.project.owlback.user.service;

import com.project.owlback.user.dto.Tokens;

import com.project.owlback.user.dto.Login;

import java.util.Optional;

public interface AuthService {

    Optional<?> login(Login login);
    Optional<?> reissue(Tokens reissue);
    Boolean logout(String accessToken);
    Optional<?> socialLogin();
}
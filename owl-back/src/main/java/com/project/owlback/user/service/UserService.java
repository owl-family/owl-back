package com.project.owlback.user.service;

import com.project.owlback.user.dto.Tokens;
import org.springframework.http.ResponseEntity;

import com.project.owlback.user.dto.Login;

import java.util.Optional;

public interface UserService{

    Optional<?> login(Login login);
    Optional<?> reissue(Tokens reissue);
    Boolean logout(String accessToken);
    Optional<?> socialLogin();
}
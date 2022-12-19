package com.project.owlback.user.service;

import com.project.owlback.user.dto.Reissue;
import org.springframework.http.ResponseEntity;

import com.project.owlback.user.dto.Login;

public interface UserService{

    public ResponseEntity<?> login(Login login);
    ResponseEntity<?> reissue(Reissue reissue);
}
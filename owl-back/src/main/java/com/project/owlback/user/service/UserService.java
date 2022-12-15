package com.project.owlback.user.service;

import com.project.owlback.user.dto.Login;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> login(Login login);

}

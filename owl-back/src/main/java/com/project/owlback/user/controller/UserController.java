package com.project.owlback.user.controller;

import com.project.owlback.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.user.dto.Login;
import com.project.owlback.user.service.UserServiceImpl;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final Response response;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated Login login) {
        try {
            return userService.login(login);
        }catch(Exception e){
            return response.invalidFields(e.getMessage());
        }
    }

}
package com.project.owlback.user.controller;

import com.project.owlback.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("check/{email}")
    public ResponseEntity<String> checkEmail(@PathVariable String email) {
        boolean isExist = userService.checkEmail(email);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

package com.project.owlback.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.user.dto.Login;
import com.project.owlback.user.service.UserServiceImpl;

@RequestMapping("/api/user")
@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Validated Login login) {
		return userService.login(login);
	}
	
}

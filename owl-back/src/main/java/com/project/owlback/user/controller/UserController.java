package com.project.owlback.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
	
	@PostMapping("/login")
	public String login() {
        User user = new User();
        user.setEmail("TestUser01@gmail.com");
        user.setPassword("010-1111-1111");

        User newUser = userRepository.save(user);
        System.out.println("newUser : " +newUser);
		return user.getEmail();
	}
	
}

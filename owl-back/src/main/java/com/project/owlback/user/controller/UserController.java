package com.project.owlback.user.controller;

import com.project.owlback.Response;
import com.project.owlback.user.dto.Tokens;
import lombok.RequiredArgsConstructor;
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
            // 로그인하면 access token과 refresh token을 모두 발급
            return userService.login(login);
        }catch(Exception e){
            return response.invalidFields(e.getMessage());
        }
    }

    // access token 재발급; front에서 access token 만료여부를 확인해서 reissue를 요청함
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Validated Tokens reissue) {
        try {
            return userService.reissue(reissue);
        }catch(Exception e){
            return response.invalidFields(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?>  logout(@RequestBody @Validated Tokens logout){
        try{
            return userService.logout(logout);
        }catch(Exception e){
            return response.invalidFields(e.getMessage());
        }
    }

}
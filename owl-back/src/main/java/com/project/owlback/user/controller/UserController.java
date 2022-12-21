package com.project.owlback.user.controller;

import com.project.owlback.Response;
import com.project.owlback.dto.ResponseDto;
import com.project.owlback.user.dto.TokenInfo;
import com.project.owlback.user.dto.Tokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.user.dto.Login;
import com.project.owlback.user.service.UserServiceImpl;

import java.util.Arrays;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final Response response;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated Login login) {
        TokenInfo tokenInfo = null;
        // 로그인하면 access token과 refresh token을 모두 발급
        try {
           tokenInfo = userService.login(login).orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(tokenInfo != null) return response.success(tokenInfo, "로그인이 성공했습니다.", HttpStatus.OK);
        else return response.fail("로그인이 실패했습니다.", HttpStatus.BAD_REQUEST);
    }

    // access token 재발급; front에서 access token 만료여부를 확인해서 reissue를 요청함
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody Tokens reissue) {
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = userService.reissue(reissue).orElse(null);
        } catch(Exception e){
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

        if(tokenInfo != null) return response.success(tokenInfo, "재발급 완료", HttpStatus.OK);
        else return response.fail("재발급 실패", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?>  logout(@RequestBody String accessToken){
        Boolean isLogout = false;
        try {
            isLogout = userService.logout(accessToken);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(isLogout) return response.success("로그아웃되었습니다.");
        else return response.fail("로그아웃이 실패했습니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/social")
    public ResponseEntity<?> socialLogin(){
        TokenInfo tokenInfo = null;
        // 로그인하면 access token과 refresh token을 모두 발급
        try {
            tokenInfo = userService.socialLogin().orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(tokenInfo != null) return response.success(tokenInfo, "로그인이 성공했습니다.", HttpStatus.OK);
        else return response.fail("signUp", HttpStatus.BAD_REQUEST);
    }

}
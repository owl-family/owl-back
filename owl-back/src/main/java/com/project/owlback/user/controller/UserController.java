package com.project.owlback.user.controller;

import com.project.owlback.user.dto.User;
import com.project.owlback.user.service.EmailService;
import com.project.owlback.user.service.UserService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final Response response;

    @GetMapping("check/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        boolean isExist = userService.findByEmail(email);

        if (isExist) {
            return response.makeResponse(HttpStatus.OK, "존재하는 이메일");
//            responseDto = ResponseDto.builder()
//                    .code(HttpStatus.OK.value())
//                    .httpStatus(HttpStatus.OK)
//                    .message("존재하는 이메일")
//                    .result(Collections.emptyList())
//                    .count(ZERO)
//                    .build();
        }
        return response.makeResponse(HttpStatus.NOT_FOUND, "존재하지 않는 이메일");
    }

    @GetMapping("signup/{email}")
    public ResponseEntity<?> sighupEmail(@PathVariable String email) throws Exception {
        String code = emailService.sendSignupEmail(email);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);

        return response.makeResponse(HttpStatus.OK, "인증 코드 발송 완료", map.size(), map);
    }

    @PutMapping("find-password")
    public ResponseEntity<?> findPassword(@RequestBody User reqUser) throws Exception {
        Optional<User> result = userService.findByEmailAndName(reqUser);

        // 이메일 또는 이름이 존재하지 않을 경우,
        if (!result.isPresent()) {
            return response.makeResponse(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 또는 이름");
        }

        String newPW = emailService.sendPasswordEmail(reqUser.getEmail());
        userService.updatePassword(result.get(), newPW);
        return response.makeResponse(HttpStatus.CREATED, "임시 비밀번호 이메일 발송 완료");

    }

    @PutMapping("change-password/{user_id}")
    public ResponseEntity<?> changePassword(@PathVariable("user_id") long userId, @RequestBody User reqUser) {
        User user = userService.findByUserId(userId);
        userService.updatePassword(user, reqUser.getPassword());

        return response.makeResponse(HttpStatus.CREATED, "비밀번호 변경 완료");
    }

}

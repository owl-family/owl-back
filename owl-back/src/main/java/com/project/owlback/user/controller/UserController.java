package com.project.owlback.user.controller;

import com.project.owlback.user.dto.ResponseDto;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.service.EmailService;
import com.project.owlback.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private static final int ZERO = 0;

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("check/{email}")
    public ResponseEntity<ResponseDto> checkEmail(@PathVariable String email) {
        boolean isExist = userService.findByEmail(email);
        ResponseDto responseDto;

        if (isExist) {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("존재하는 이메일")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();

        } else {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("존재하지 않는 이메일")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();
        }

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    @GetMapping("signup/{email}")
    public ResponseEntity<ResponseDto> sighupEmail(@PathVariable String email) throws Exception {
        String code = emailService.sendSignupEmail(email);

        HashMap<String, String> map = new HashMap<>();
        List<HashMap<String, String>> list = new ArrayList<>();

        map.put("code", code);
        list.add(map);

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("인증 코드 발송 완료")
                .result(Arrays.asList(list.toArray()))
                .count(list.size())
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    @PutMapping("find-password")
    public ResponseEntity<ResponseDto> findPassword(@RequestBody User reqUser) throws Exception {
        Optional<User> result = userService.findByEmailAndName(reqUser);
        ResponseDto responseDto;

        // 이메일 또는 이름이 존재하지 않을 경우,
        if (!result.isPresent()) {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("존재하지 않는 이메일 또는 이름")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();

        } else {
            String newPW = emailService.sendPasswordEmail(reqUser.getEmail());
            userService.updatePassword(result.get(), newPW);

            responseDto = ResponseDto.builder()
                    .code(HttpStatus.CREATED.value())
                    .httpStatus(HttpStatus.CREATED)
                    .message("임시 비밀번호 이메일 발송 완료")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();
        }

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    @PutMapping("change-password/{user_id}")
    public ResponseEntity<ResponseDto> changePassword(@PathVariable("user_id") long userId, @RequestBody User reqUser) {
        User user = userService.findByUserId(userId);
        userService.updatePassword(user, reqUser.getPassword());

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.CREATED.value())
                .httpStatus(HttpStatus.CREATED)
                .message("비밀번호 변경 완료")
                .result(Collections.emptyList())
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

}

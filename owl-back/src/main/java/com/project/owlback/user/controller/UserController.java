package com.project.owlback.user.controller;

import com.project.owlback.user.dto.CreateUserReq;
import com.project.owlback.user.dto.UpdateInfo;
import com.project.owlback.user.dto.ResponseDto;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.service.EmailService;
import com.project.owlback.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private static final int ZERO = 0;

    private final UserService userService;
    private final EmailService emailService;

    /**
     * 회원가입 API
     * [POST] /api/users
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@RequestBody @Valid CreateUserReq createUserReq) {
        userService.createUser(createUserReq);

        ResponseDto responseDto;

        if (!createUserReq.getPassword().equals(createUserReq.getPasswordCheck())) {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("비밀번호가 비밀번호 확인 값과 다름")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("회원가입 완료")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();
        }
        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    /**
     * 닉네임 중복체크 API
     * [GET] /api/check2/{nickname}
     */
    @GetMapping("check2/{nickname}")
    public ResponseEntity<ResponseDto> checkNickname(@PathVariable String nickname) {
        boolean isExist = userService.findByNickname(nickname);
        ResponseDto responseDto;

        if (!isExist) {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("닉네임 중복 확인 완료")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();

        } else {
            responseDto = ResponseDto.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("이미 존재하는 닉네임")
                    .result(Collections.emptyList())
                    .count(ZERO)
                    .build();
        }

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
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

    /**
     * 회원 정보 수정 API
     * [PUT] /api/users/{user_id}
     */
    @PutMapping("{user_id}")
    public ResponseEntity<ResponseDto> UpdateInfo(@PathVariable("user_id") Long userId, @RequestBody UpdateInfo updateInfoReq) {
        userService.updateInfo(userId, updateInfoReq);

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.CREATED.value())
                .httpStatus(HttpStatus.CREATED)
                .message("회원 정보 수정 완료")
                .result(Collections.emptyList())
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    /**
     * 회원 정보 조회 API
     * [GET] /api/users/{user_id}
     */
    @GetMapping("{user_id}")
    public ResponseEntity<ResponseDto> getInfo(@PathVariable("user_id") Long userId) {

        User user = userService.findByUserId(userId);
        List<Object> updateInfoList = new ArrayList<>();
        updateInfoList.add(new UpdateInfo(user.getNickname(), user.getImgFile(), user.getIntroduction()));

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원 정보 조회 완료")
                .result(updateInfoList)
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    /**
     * 회원 탈퇴 API
     * [PUT] /api/users/delete/{user_id}
     */
    @PutMapping("delete/{user_id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.CREATED.value())
                .httpStatus(HttpStatus.CREATED)
                .message("회원 탈퇴 완료")
                .result(Collections.emptyList())
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }



}

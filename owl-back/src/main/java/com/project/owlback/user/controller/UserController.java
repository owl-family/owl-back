package com.project.owlback.user.controller;

import com.project.owlback.user.dto.req.*;
import com.project.owlback.user.dto.res.GetUserInfoRes;
import com.project.owlback.user.model.User;
import com.project.owlback.user.model.UserImg;
import com.project.owlback.user.service.EmailService;
import com.project.owlback.user.service.UserImgService;
import com.project.owlback.user.service.UserService;
import com.project.owlback.util.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import com.project.owlback.user.dto.res.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.user.service.AuthServiceImpl;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserImgService userImgService;
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated Login login) {
        TokenInfo tokenInfo = null;
        // 로그인하면 access token과 refresh token을 모두 발급
        try {
           tokenInfo = authService.login(login).orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(tokenInfo != null) return Response.makeResponse(HttpStatus.CREATED, "로그인이 성공했습니다.", 1, tokenInfo);
        else return Response.makeResponse(HttpStatus.FORBIDDEN, "로그인이 실패했습니다.");
    }

    // access token 재발급; front에서 access token 만료여부를 확인해서 reissue를 요청함
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody Tokens reissue) {
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = authService.reissue(reissue).orElse(null);
        } catch(Exception e){
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

        if(tokenInfo != null) return Response.makeResponse(HttpStatus.CREATED, "재발급 완료", 1, tokenInfo);
        else return Response.makeResponse(HttpStatus.FORBIDDEN, "재발급 실패");
    }

    @PostMapping("/logout")
    public ResponseEntity<?>  logout(@RequestBody String accessToken){
        Boolean isLogout = false;
        try {
            isLogout = authService.logout(accessToken);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(isLogout) return Response.makeResponse(HttpStatus.OK, "로그아웃되었습니다.");
        else return Response.makeResponse(HttpStatus.FORBIDDEN, "로그아웃이 실패했습니다.");
    }

    // session에 저장된 정보를 가지고 토큰을 만들어서 보내줌
    @GetMapping("/login/social")
    public ResponseEntity<?> socialLogin(HttpServletResponse response) throws IOException {
        // 로그인하면 access token과 refresh token을 모두 발급
        try {
            return authService.socialLogin();
        } catch(Exception e){
            log.info(e.getMessage());
        }

        return Response.notFound("로그인이 실패했습니다.");
    }

    /**
     * 회원가입 API
     * [POST] /api/users
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid PostUserReq postUserReq) {
        userService.createUser(postUserReq);
        if (!postUserReq.getPassword().equals(postUserReq.getPasswordCheck()))
            return Response.makeResponse(HttpStatus.NOT_FOUND,"비밀번호가 비밀번호 확인 값과 다름");
        else
            return Response.makeResponse(HttpStatus.OK,"회원가입 완료");
    }

    /**
     * 닉네임 중복체크 API
     * [GET] /api/check2/{nickname}
     */
    @GetMapping("check2/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        boolean isExist = userService.findByNickname(nickname);

        if (!isExist)
            return Response.makeResponse(HttpStatus.NOT_FOUND,"닉네임 중복 확인 완료");
        else
            return Response.makeResponse(HttpStatus.OK,"이미 존재하는 닉네하는 닉네임");
    }



    /**
     * 회원 정보 수정 API
     * [PUT] /api/users/{user_id}
     */
    @PutMapping("{user_id}")
    public ResponseEntity<?> UpdateInfo(@PathVariable("user_id") Long userId, @RequestBody PutUserInfoReq putUserInfoReq) {
        userService.updateInfo(userId,putUserInfoReq);

        return Response.makeResponse(HttpStatus.CREATED, "회원 정보 수정 완료");
    }


    /**
     * 회원 정보 조회 API
     * [GET] /api/users/{user_id}
     */
    @GetMapping("{user_id}")
    public ResponseEntity<?> getInfo(@PathVariable("user_id") Long userId) {

        User user = userService.findByUserId(userId);
        Object getUserInfo = new GetUserInfoRes(user.getNickname(), user.getIntroduction(),user.getUserImg());
        return Response.makeResponse(HttpStatus.OK, "회원 정보 조회 완료",1, getUserInfo);
    }

    /**
     * 회원 탈퇴 API
     * [PUT] /api/users/delete/{user_id}
     */
    @PutMapping("delete/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);

        return Response.makeResponse(HttpStatus.CREATED, "회원 탈퇴 완료");
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile files) throws IOException {

        System.out.println("files: " + files.toString());

        UserImg userImg = new UserImg();

        String sourceFileName = files.getOriginalFilename();
        // getOriginalFilename: 전송한 파일의 이름 (ex. image2.jpg)

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        // FilenameUtils.getExtension: .(점) 뒤를 반환

        FilenameUtils.removeExtension(sourceFileName);
        // .(점) 앞을 반환

        File destinationFile;
        String destinationsFileName;
        String fileUrl = "/Users/somyeong/Desktop/owl/owl-back/owl-back/src/main/resources/img/";

        do {

            //알수없는 파일 이름으로 변환
            destinationsFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationsFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        userImg.setFileName(destinationsFileName);
        userImg.setFileOriginalName(sourceFileName);
        userImg.setFileUrl(fileUrl);
        userImgService.save(userImg);

        return "redirect:/upload";

    }

    @GetMapping("check/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        boolean isExist = userService.findByEmail(email);

        if (isExist) {
            return Response.ok("존재하는 이메일");
        }
        return Response.notFound("존재하지 않는 이메일");
    }

    @GetMapping("signup/{email}")
    public ResponseEntity<?> sighupEmail(@PathVariable String email) throws Exception {
        String code = emailService.sendSignupEmail(email);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);

        return Response.makeResponse(HttpStatus.OK, "인증 코드 발송 완료", map.size(), map);
    }

    @PutMapping("find-password")
    public ResponseEntity<?> findPassword(@RequestBody UserFindPasswordDto userFindPasswordDto) throws Exception {
        Optional<User> result = userService.findByEmailAndName(userFindPasswordDto);

        // 이메일 또는 이름이 존재하지 않을 경우,
        if (result.isEmpty()) {
            return Response.makeResponse(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 또는 이름");
        }

        String newPW = emailService.sendPasswordEmail(userFindPasswordDto.getEmail());
        userService.updatePassword(result.get(), newPW);
        return Response.created("임시 비밀번호 이메일 발송 완료");
    }

    @PutMapping("change-password/{user_id}")
    public ResponseEntity<?> changePassword(@PathVariable("user_id") long userId, @RequestBody PasswordDto passwordDto) {
        User user = userService.findByUserId(userId);
        userService.updatePassword(user, passwordDto.getPassword());

        return Response.created("비밀번호 변경 완료");
    }

    /**
     * 회원 프로필 사진 수정 API
     * [PUT] /api/users/img/{user_id}
     */
    @PostMapping("img/{user_id}")
    public ResponseEntity<?> UpdateProfileImg(@PathVariable("user_id") Long userId, @RequestPart MultipartFile files) throws IOException {

        System.out.println("회원프로필 수정 컨트롤러 호출 ");

        System.out.println("files: "+ files.toString());

        UserImg userImg = new UserImg();

        String sourceFileName = files.getOriginalFilename();
        // getOriginalFilename: 전송한 파일의 이름 (ex. image2.jpg)

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        // FilenameUtils.getExtension: .(점) 뒤를 반환

        FilenameUtils.removeExtension(sourceFileName);
        // .(점) 앞을 반환

        File destinationFile;
        String destinationsFileName;
        String fileUrl = "/Users/somyeong/Desktop/owl/owl-back/owl-back/src/main/resources/img/";

        do{

            //알수없는 파일 이름으로 변환
            destinationsFileName = RandomStringUtils.randomAlphanumeric(32)+"."+sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationsFileName);
        }while(destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        userImg.setFileName(destinationsFileName);
        userImg.setFileOriginalName(sourceFileName);
        userImg.setFileUrl(fileUrl);
        userImgService.save(userId,userImg);

        return Response.makeResponse(HttpStatus.CREATED,"회원 프로필 사진 변경 완료");
    }

}
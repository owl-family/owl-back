package com.project.owlback.user.controller;

import com.project.owlback.user.dto.*;
import com.project.owlback.user.dto.req.PostUserReq;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.user.dto.res.GetUserInfoRes;
import com.project.owlback.user.service.EmailService;
import com.project.owlback.user.service.UserImgService;
import com.project.owlback.user.service.UserService;
import com.project.owlback.util.Response;
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

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private static final int ZERO = 0;

    private final UserService userService;
    private final EmailService emailService;

    private final UserImgService userImgService;

    private final Response response;

    /**
     * 회원가입 API
     * [POST] /api/users
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid PostUserReq postUserReq) {
        userService.createUser(postUserReq);
        if (!postUserReq.getPassword().equals(postUserReq.getPasswordCheck()))
            return response.makeResponse(HttpStatus.NOT_FOUND,"비밀번호가 비밀번호 확인 값과 다름");
        else
            return response.makeResponse(HttpStatus.OK,"회원가입 완료");
    }

    /**
     * 닉네임 중복체크 API
     * [GET] /api/check2/{nickname}
     */
    @GetMapping("check2/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        boolean isExist = userService.findByNickname(nickname);

        if (!isExist)
            return response.makeResponse(HttpStatus.NOT_FOUND,"닉네임 중복 확인 완료");
        else
            return response.makeResponse(HttpStatus.OK,"이미 존재하는 닉네하는 닉네임");
    }



    /**
     * 회원 정보 수정 API
     * [PUT] /api/users/{user_id}
     */
    @PutMapping("{user_id}")
    public ResponseEntity<?> UpdateInfo(@PathVariable("user_id") Long userId, @RequestBody PutUserInfoReq putUserInfoReq) {
        userService.updateInfo(userId,putUserInfoReq);

        return response.makeResponse(HttpStatus.CREATED, "회원 정보 수정 완료");
    }


    /**
     * 회원 정보 조회 API
     * [GET] /api/users/{user_id}
     */
    @GetMapping("{user_id}")
    public ResponseEntity<?> getInfo(@PathVariable("user_id") Long userId) {

        User user = userService.findByUserId(userId);
        Object getUserInfo = new GetUserInfoRes(user.getNickname(), user.getIntroduction(),user.getUserImg());
        return response.makeResponse(HttpStatus.OK, "회원 정보 조회 완료",1, getUserInfo);
    }

    /**
     * 회원 탈퇴 API
     * [PUT] /api/users/delete/{user_id}
     */
    @PutMapping("delete/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);

        return response.makeResponse(HttpStatus.CREATED, "회원 탈퇴 완료");
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile files) throws IOException {

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
        userImgService.save(userImg);

        return "redirect:/upload";

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

        return response.makeResponse(HttpStatus.CREATED,"회원 프로필 사진 변경 완료");

    }



}

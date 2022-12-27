package com.project.owlback.studygroup.controller;

import com.project.owlback.codereview.model.StudyGroup;
import com.project.owlback.studygroup.dto.StudyInviteReqDto;
import com.project.owlback.studygroup.dto.StudyJoinReqDto;
import com.project.owlback.studygroup.service.EmailService;
import com.project.owlback.studygroup.service.StudyGroupService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;


@RestController
@Slf4j
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    private final EmailService emailService;

    // 스터디 가입 요청 API
    @PostMapping("/{studyId}/join/{userId}")
    public ResponseEntity<?> StudyJoinRequest(@PathVariable long studyId, @PathVariable long userId, @RequestBody StudyJoinReqDto reqDto) {

        log.info("studyId : {}, userId : {}, message : {}", studyId, userId, reqDto.getMessage());

        boolean isSend = studyGroupService.isJoinRequest(studyId, userId);
        log.info("already send join request is : {}", isSend);

        // 이미 가입 신청이 되어있다면,
        if (isSend) {
            return Response.makeResponse(HttpStatus.CONFLICT, "이미 가입요청한 스터디입니다.");
        }

        try {
            long id = studyGroupService.joinRequest(studyId, userId, reqDto.getMessage());
            log.info("study member saved successfully id : {}", id);

            return Response.makeResponse(HttpStatus.CREATED, "가입요청 성공");
        } catch (Exception e) {
            return Response.badRequest("가입요청 실패");
        }
    }

    // 스터디 가입 승인 API
    @PutMapping("/{studyId}/join/{userId}")
    public ResponseEntity<?> StudyJoinAccept(@PathVariable long studyId, @PathVariable long userId) {

        log.info("studyId : {}, userId : {}", studyId, userId);

        try {
            long id = studyGroupService.joinAccept(studyId, userId);
            log.info("member accepted study successfully id : {}", id);

            return Response.ok("가입승인 성공");
        } catch (NoSuchElementException s) {
            return Response.noContent("가입요청을 하지 않은 유저이거나 존재하지 않는 유저입니다.");
        } catch (Exception e) {
            return Response.badRequest("가입승인 실패");
        }

    }

    // 스터디 가입 초대 API
    @PostMapping("/{studyId}/invite")
    public ResponseEntity<?> StudyInviteRequest(@PathVariable long studyId, @RequestBody StudyInviteReqDto reqDto) {

        log.info("studyId : {}, emails : {}", studyId, reqDto.getEmails());

        // email이 없는 경우
        if (reqDto.getEmails() == null || reqDto.getEmails().size() == 0) {
            return Response.noContent("입력으로 들어온 이메일이 없습니다.");
        }
        try {
            final List<String> emails = studyGroupService.inviteRequest(studyId, reqDto);
            log.info("send target emails : {}", emails);

            final StudyGroup studyGroup = studyGroupService.getStudyGroup(studyId);
            emailService.sendInviteEmail(emails, studyGroup);

            return Response.makeResponse(HttpStatus.CREATED, "study invite request success");
        } catch (Exception e) {
            return Response.badRequest("스터디 가입 초대 실패");
        }
    }

    // 스터디 가입 초대 승인 API
    @PutMapping("/{studyId}/invite/{userId}/{joinCode}")
    public ResponseEntity<?> StudyInviteAccept(@PathVariable long studyId, @PathVariable long userId, @PathVariable String joinCode) {

        log.info("studyId : {}, userId:{}, joinCode : {}", studyId, userId, joinCode);

        try {
            long id = studyGroupService.inviteAccept(studyId, userId, joinCode);
            log.info("update member state id : {}", id);
            if (id == 0) {
                log.info("joinCode is not equal");
                return Response.noContent("초대코드가 올바르지 않습니다.");
            }
            return Response.makeResponse(HttpStatus.OK, "스터디 가입 초대 승인 성공");
        } catch (NoSuchElementException s) {
            log.info("not invite user");

            return Response.noContent("초대한 유저가 아닙니다.");
        } catch (Exception e) {
            return Response.badRequest("스터디 가입 초대 승인 실패");
        }
    }
}

package com.project.owlback.studygroup.controller;

import com.project.owlback.studygroup.dto.req.StudyMakeDto;
import com.project.owlback.studygroup.dto.res.AppliedMemberRes;
import com.project.owlback.studygroup.dto.res.StudyDetailInfo;
import com.project.owlback.studygroup.dto.res.StudyInfo;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.model.StudyCriteria;
import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;
import com.project.owlback.studygroup.dto.req.StudyJoinReqDto;
import com.project.owlback.studygroup.model.StudyJoinProcess;
import com.project.owlback.studygroup.model.StudyStatus;
import com.project.owlback.studygroup.repository.StudyCriteriaRepository;
import com.project.owlback.studygroup.repository.StudyJoinProcessRepository;
import com.project.owlback.studygroup.repository.StudyStatusRepository;
import com.project.owlback.studygroup.service.StudyEmailService;

import com.project.owlback.studygroup.service.StudyGroupService;
import com.project.owlback.user.model.User;
import com.project.owlback.user.repository.UserRepository;
import com.project.owlback.user.service.EmailServiceImpl;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@Slf4j
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    private final StudyEmailService emailService;

    private final UserRepository userRepository;
    private final StudyCriteriaRepository studyCriteriaRepository;
    private final StudyJoinProcessRepository studyJoinProcessRepository;
    private final StudyStatusRepository studyStatusRepository;

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
        }catch(NoSuchElementException s){
            log.debug(s.getMessage());
                return Response.noContent("존재하지 않는 스터디거나 유저입니다.");
        } catch (Exception e) {
            log.debug(e.getMessage());
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
        } catch (NoSuchElementException s) {
            return Response.noContent("존재하지 않는 유저입니다.");
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
            if (id == 0L) {
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
    @GetMapping("/{studyGroupId}/members")
    public ResponseEntity<?> members(@PathVariable Long studyGroupId) {
        ArrayList<StudyMemberRes> members = null;
        try {
            members = studyGroupService.members(studyGroupId).orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(members == null) return Response.notFound("존재하지 않는 스터디 입니다.");
        return Response.makeResponse(HttpStatus.CREATED, "스터디원 검색이 완료되었습니다.", members.size(), members);
    }

    @PutMapping("/{studyGroupId}/expiration")
    public ResponseEntity<?> expire(@PathVariable Long studyGroupId) {
        try {
            studyGroupService.expire(studyGroupId);
        } catch(Exception e){
            log.info(e.getMessage());
            return Response.notFound("스터디 종료에 실패했습니다.");
        }

        return Response.ok("스터디를 성공적으로 종료했습니다.");
    }

    @GetMapping("/criteria")
    public ResponseEntity<?> criteria(){
        List<StudyCriteria> criteria = null;
        try {
            criteria = studyGroupService.criteria().orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(criteria == null) return Response.notFound("가입 기준 목록을 가져오는 것에 실패했습니다.");
        return Response.makeResponse(HttpStatus.CREATED, "가입 기준 목록을 성공적으로 가져왔습니다.", criteria.size(), criteria);
    }

    @GetMapping("/join-process")
    public ResponseEntity<?> joinProcesses(){
        List<StudyJoinProcess> joinProcesses = null;
        try {
            joinProcesses = studyGroupService.joinProcesses().orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(joinProcesses == null) return Response.notFound("가입 방식 목록을 가져오는 것에 실패했습니다.");
        return Response.makeResponse(HttpStatus.CREATED, "가입 방식 목록을 성공적으로 가져왔습니다.", joinProcesses.size(), joinProcesses);
    }

    @GetMapping("/{studyGroupId}/join/{userId}")
    public ResponseEntity<?> applied(@PathVariable Long studyGroupId, @PathVariable Long userId){
        ArrayList<AppliedMemberRes> appliedMembers = null;
        try {
            appliedMembers = studyGroupService.applied(studyGroupId).orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(appliedMembers == null) return Response.notFound("존재하지 않는 스터디 입니다.");
        return Response.makeResponse(HttpStatus.CREATED, "가입 신청한 스터디원 검색이 완료되었습니다.", appliedMembers.size(), appliedMembers);
    }

    ////////////////////////////////////////////////////////////////////////////
    @PostMapping
    public ResponseEntity<?> makeStudy(@RequestBody StudyMakeDto studyMakeDto) {
        boolean isCreated = studyGroupService.makeStudy(studyMakeDto);

        if (isCreated) {
            return  Response.created("스터디 생성 완료");
        }
        return Response.badRequest("스터디 생성 실패");
    }

    @PutMapping("{studyId}/setting")
    public ResponseEntity<?> changeStudySetting(@PathVariable long studyId, @RequestBody StudyMakeDto studyMakeDto) {
        studyGroupService.changeStudy(studyId, studyMakeDto);
        return Response.created("스터디 정보 변경 완료");
    }

    @GetMapping
    public ResponseEntity<?> getStudies(@RequestParam(required = false) long userId, @RequestParam(required = false) String search, @RequestParam(required = false) String query) {
        // 스터디 맴버 테이블에 추가
        List<StudyInfo> studyInfoTests = studyGroupService.getStudies(userId, search, query);
        return Response.makeResponse(HttpStatus.OK, "스터디 목록 조회 완료", studyInfoTests.size(), studyInfoTests);
    }

    @GetMapping("{studyId}")
    public ResponseEntity<?> getStudyDetail(@PathVariable long studyId) {
        StudyDetailInfo studyDetailInfo = studyGroupService.getStudyDetailInfo(studyId);
        return Response.makeResponse(HttpStatus.OK, "스터디 정보 조회 완료");
//        return Response.makeResponse(HttpStatus.OK, "스터디 정보 조회 완료", 1, studyDetailInfo);
    }

}

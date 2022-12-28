package com.project.owlback.studygroup.controller;

import com.project.owlback.studygroup.dto.StudyCriteria;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.service.StudyGroupService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/studies")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

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

}
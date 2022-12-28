package com.project.owlback.studygroup.controller;

import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.service.StudyGroupService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/api/studies")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping("/{studyGroupId}/members")
    public ResponseEntity<?> memberList(@PathVariable Long studyGroupId) {
        ArrayList<StudyMemberRes> list = null;
        try {
             list = studyGroupService.memberList(studyGroupId).orElse(null);
        } catch(Exception e){
            log.info(e.getMessage());
        }

        if(list == null) return Response.notFound("존재하지 않는 스터디 입니다.");
        return Response.makeResponse(HttpStatus.CREATED, "스터디원 검색이 완료되었습니다.", list.size(), list);
    }

}
package com.project.owlback.studygroup.controller;

import com.project.owlback.studygroup.service.StudyGroupService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/studies")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

}
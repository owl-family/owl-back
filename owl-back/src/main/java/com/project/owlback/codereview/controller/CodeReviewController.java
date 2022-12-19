package com.project.owlback.codereview.controller;

import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.service.CodeReviewService;
import com.project.owlback.codereview.service.CodeReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/codereviews")
public class CodeReviewController {
    private final CodeReviewService codeReviewService;

    @PostMapping("/{codeReviewId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Integer codeReviewId,
                                        @RequestBody CodeReviewCommentReqDto reqDto) {
        log.info("codeReviewId : {}", codeReviewId);
        reqDto.setCodeReviewId(codeReviewId);
        log.info("{}", reqDto);

        final Integer id = codeReviewService.addComment(reqDto);
        log.info("comment saved successfully id : {}", id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comments/{codeCommentId}")
    public ResponseEntity<?> likeComment(@PathVariable Integer codeCommentId) {
        log.info("codeCommentId : {}", codeCommentId);

        CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();
        reqDto.setCodeCommentId(codeCommentId);
        log.info("{}", reqDto);

        final int likeCount = codeReviewService.likeComment(reqDto);

        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }


}

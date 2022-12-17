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

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/codereviews")
public class CodeReviewController {
    private final CodeReviewService codeReviewService;

    @PostMapping("/{codeReviewId}/history/{versionNum}/comments")
    public ResponseEntity<?> addComment(@PathVariable Integer codeReviewId, @PathVariable Integer versionNum,
                                        @RequestBody CodeReviewCommentReqDto reqDto) {
        log.info("codeReviewId : {}, versionNum : {}", codeReviewId, versionNum);
        log.info("{}", reqDto);
        reqDto.setCodeReviewId(codeReviewId);
        reqDto.setVersionNum(versionNum);

        codeReviewService.addComment(reqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{codeReviewId}/history/{versionNum}/comments/{codeCommentId}")
    public ResponseEntity<?> likeComment(@PathVariable Integer codeReviewId, @PathVariable Integer versionNum,
                                         @PathVariable Integer codeCommentId,
                                         @RequestBody CodeReviewCommentReqDto reqDto) {
        log.info("codeReviewId : {}, versionNum : {}, codeCommentId : {}", codeReviewId, versionNum, codeCommentId);
        log.info("{}", reqDto);

        reqDto.setCodeCommentId(codeCommentId);

        final int likeCount = codeReviewService.likeComment(reqDto);

        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }
}

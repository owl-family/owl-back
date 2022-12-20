package com.project.owlback.codereview.controller;

import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.dto.ResponseDto;
import com.project.owlback.codereview.service.CodeReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/codereviews")
public class CodeReviewController {
    private final CodeReviewService codeReviewService;

    @PostMapping("/{code_review_id}/comments")
    public ResponseEntity<?> addComment(@PathVariable("code_review_id") Long codeReviewId,
                                        @RequestBody CodeReviewCommentReqDto reqDto) {
        log.info("codeReviewId : {}", codeReviewId);
        reqDto.setCodeReviewId(codeReviewId);
        log.info("{}", reqDto);

        try {
            final Long id = codeReviewService.addComment(reqDto);
            log.info("comment saved successfully id : {}", id);

            return new ResponseEntity<>(
                    ResponseDto.create(HttpStatus.OK, "comment saved successfully", Collections.emptyList()),
                    HttpStatus.OK);
        } catch(Exception e) {
            return badRequest();
        }
    }

    @PutMapping("/comments/{code_comment_id}")
    public ResponseEntity<?> likeComment(@PathVariable("code_comment_id") Long codeCommentId) {
        log.info("codeCommentId : {}", codeCommentId);

        CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();
        reqDto.setCodeCommentId(codeCommentId);
        log.info("request Dto : {}", reqDto);

        try {
            final int likeCount = codeReviewService.likeComment(reqDto);

            Map<String, Integer> result = new HashMap<>();
            result.put("likeCount", likeCount);

            List<Map<?, ?>> list = new ArrayList<>();
            list.add(result);

            return new ResponseEntity<>(
                    ResponseDto.create(HttpStatus.OK, "OK", list),
                    HttpStatus.OK);
        } catch(Exception e) {
            return badRequest();
        }
    }

    @GetMapping("/comments")
    // key : title, contents, writer (포스트 작성자 nickname)
    public ResponseEntity<?> getMyComments(@RequestParam String key, @RequestParam String word,
                                           @PageableDefault(size=20, sort="createdDate",
                                                   direction= Sort.Direction.DESC) Pageable pageable) {

        final Page<CodeCommentResDto> response = codeReviewService.getMyComments(key, word, pageable);
        List<Page<?>> list = new ArrayList<>();
        list.add(response);
        return new ResponseEntity<>(
                ResponseDto.create(HttpStatus.OK, "OK", list),
                HttpStatus.OK);
    }

    public ResponseEntity<?> badRequest() {
        return new ResponseEntity<>(
                ResponseDto.create(HttpStatus.BAD_REQUEST, "bad request", Collections.emptyList()),
                HttpStatus.BAD_REQUEST);
    }
}

package com.project.owlback.codereview.controller;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.service.CodeReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/codereviews")
public class CodeReviewController {
    final private CodeReviewService service;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> codeReviewList(
            @RequestParam String key, @RequestParam(defaultValue = "0") Integer id,
            @PageableDefault(size = 20, sort = "modifiedDate", direction = Sort.Direction.DESC) Pageable pageable)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        log.info("key : {}, id : {}, pageable : {}", key, id, pageable);

        Page<CodeReviewItemDto> list = null;
        list = service.codeReviewList(key, id, pageable);
        log.info("list : {}", list);

        if (list == null || list.getTotalPages() == 0) {
            resultMap.put("message", "no data");
            status = HttpStatus.NO_CONTENT;
        } else {
            resultMap.put("message", "success");
            resultMap.put("list", list);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> codeReviewSearch(
            @RequestParam String key, @RequestParam String word,
            @PageableDefault(size = 20) Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        log.info("key : {}, word : {}, pageable : {}", key, word, pageable);

        try {
            Page<CodeReviewItemDto> list = service.codeReviewSearch(key, word, pageable);
            log.info("list : {}", list);

            if (list == null || list.getTotalPages() == 0) {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            } else {
                resultMap.put("message", "success");
                resultMap.put("list", list);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{codeReviewId}/history/{versionNum}")
    public ResponseEntity<Map<String, Object>> codeReviewHistoryDetail(
            @PathVariable int codeReviewId, @PathVariable int versionNum, HttpServletRequest request,
            @PageableDefault(size = 10) Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        log.info("codeReviewId : {}, versionNum : {}", codeReviewId, versionNum);
        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        int userId = 2;
        log.info("userId : {}", userId);
        try {
            CodeHistoryDetailDto codeHistory = service.codeReviewHistoryDetail(codeReviewId, versionNum, userId, pageable);
            log.info("codeHistory : {}", codeHistory);

            if (codeHistory != null) {
                resultMap.put("message", "success");
                resultMap.put("codeHistory", codeHistory);
                status = HttpStatus.OK;
            }
        } catch (NoSuchElementException s) {
            resultMap.put("message", "no data");
            status = HttpStatus.NO_CONTENT;

        } catch (Exception e){
            resultMap.put("message","fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/history/{historyId}/comments")
    public ResponseEntity<?> codeReviewComments(
            @PathVariable int historyId, HttpServletRequest request,
            @PageableDefault(size = 20, sort = "modifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        request.getHeader("access-token");
        int userId = 2;
        try {
            Page<CodeCommentDetailDto> list = service.codeReviewComments(historyId, userId, pageable);
            resultMap.put("message", "success");
            resultMap.put("list", list);
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/history/{historyId}/comments/{startLine}")
    public ResponseEntity<?> codeReviewCommentsDetail(
            @PathVariable int historyId, @PathVariable int startLine, HttpServletRequest request,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        log.info("historyId : {}, startLine : {}", historyId, startLine);

        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        int userId = 2;
        log.info("userId : {}", userId);
        try {
            Page<CodeCommentDetailDto> comments = service.codeReviewCommentsDetail(historyId, startLine, userId, pageable);
            log.info("comments : {}", comments);

            if (comments == null || comments.getTotalPages() == 0) {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            } else {
                resultMap.put("message", "success");
                resultMap.put("comments", comments);
                status = HttpStatus.OK;
            }

        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(resultMap, status);
    }


    @GetMapping("/tag/{word}")
    public ResponseEntity<Map<String, Object>> codeReviewRelativeTags(@PathVariable String word) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        log.info("word : {}", word);

        try {
            List<String> tags = service.getRelativeTags(word);
            log.info("tags : {}", tags);

            if (tags == null || tags.size() == 0) {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            } else {
                resultMap.put("message", "success");
                resultMap.put("tags", tags);
                status = HttpStatus.OK;
            }

        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(resultMap, status);
    }

}

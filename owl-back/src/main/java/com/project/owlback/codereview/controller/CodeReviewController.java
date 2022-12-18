package com.project.owlback.codereview.controller;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.service.CodeReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CodeReviewController {
    @Autowired
    CodeReviewService service;

    @GetMapping("/codereviews")
    public ResponseEntity<Map<String, Object>> codeReviewList(@RequestParam String key, @RequestParam String id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        int param;

        if (id == null || id.length() == 0) {
            param = 0;
        } else {
            param = Integer.parseInt(id);
        }

        List<CodeReviewItemDto> list = null;
        try {
            list = service.codeReviewList(key, param);
            if (list != null && list.size() > 0) {
                resultMap.put("message", "success");
                resultMap.put("list", list);
                status = HttpStatus.OK;
            } else {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/codereviews/search")
    public ResponseEntity<Map<String, Object>> codeReviewSearch(@RequestParam String key, @RequestParam String word) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            List<CodeReviewItemDto> list = service.codeReviewSearch(key, word);
            if (list != null && list.size() > 0) {
                resultMap.put("message", "success");
                resultMap.put("list", list);
                status = HttpStatus.OK;
            } else {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/codereviews/tag/{tag}")
    public ResponseEntity<Map<String, Object>> codeReviewTag(@PathVariable String tag) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/codereviews/history/{historyId}/comments/{startLine}")
    public ResponseEntity<Map<String, Object>> codeReviewCommentsDetail(@PathVariable int historyId, @PathVariable int startLine, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        int userId = 2;
        try {
            List<CodeCommentDetailDto> comments = service.codeReviewCommentsDetail(historyId, startLine, userId);
            if (comments == null || comments.size() == 0) {
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

    @GetMapping("/codereviews/{codeReviewId}/history/{versionNum}")
    public ResponseEntity<Map<String, Object>> codeReviewHistoryDetail(@PathVariable int codeReviewId, @PathVariable int versionNum, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        int userId = 2;
        try {
            CodeHistoryDetailDto codeHistory = service.codeReviewHistoryDetail(codeReviewId, versionNum, userId);

            if (codeHistory != null) {
                resultMap.put("message", "success");
                resultMap.put("codeHistory", codeHistory);
                status = HttpStatus.OK;
            } else {
                resultMap.put("message", "no data");
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            resultMap.put("message", "fail");
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(resultMap, status);
    }
}

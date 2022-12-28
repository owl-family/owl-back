package com.project.owlback.codereview.controller;


import com.project.owlback.codereview.dto.*;
import com.project.owlback.codereview.service.CodeReviewService;
import com.project.owlback.util.Response;
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

import java.util.*;

@RestController()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/codereviews")
public class CodeReviewController {
    private final CodeReviewService codeReviewService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CodeReviewPostDto codeReviewPostDto) {
        // codereview 정보
        log.info("info log={}", codeReviewPostDto);
        try {
        	final Long x = codeReviewService.create(codeReviewPostDto);
        	return Response.makeResponse(HttpStatus.OK, "sucess post codereview",1,x);
        } catch (Exception e) {
			return Response.badRequest("fail");
		}
    }

    @PostMapping("/{id}/history")
    public ResponseEntity<?> createHistory(@RequestBody CodeHistoryPostDto codeHistoryPostDto, @PathVariable Long id) {
        log.info("info log={}", codeHistoryPostDto);
        log.info("info log={}", id);
        try {
        	final Long x = codeReviewService.createHistory(codeReviewService.setCodeReviewToCodeHistory(codeHistoryPostDto, id), codeHistoryPostDto.getTag());
        	log.info("info log={}", x);
        	return Response.makeResponse(HttpStatus.OK, "sucess post history",1,x);
        }catch(Exception e) {
        	return Response.badRequest("fail");
        }
    }

    @GetMapping("/{codeReviewId}/history")
    public ResponseEntity<?> getCodeReviewHistory(@PathVariable Long codeReviewId) {
        log.info("codeReview id={}", codeReviewId);
        try {
        	final List<CodeHistoryGetDto> codeHistoryList = codeReviewService.getCodeReviewHistory(codeReviewId);
        	log.info("codeHistoryList={}", codeHistoryList);
        	return Response.makeResponse(HttpStatus.OK,"sucess get CodeHistoryAll",codeHistoryList.size(),codeHistoryList);
        }catch(NoSuchElementException ne) {
        	return Response.noContent("no history");
        }catch(Exception e) {
        	return Response.badRequest("fail");
        }
    }

    @PostMapping("/{codeReviewId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long codeReviewId,
                                        @RequestBody CodeReviewCommentReqDto reqDto) {
        log.info("codeReviewId : {}", codeReviewId);
        reqDto.setCodeReviewId(codeReviewId);
        log.info("{}", reqDto);

        try {
            final Long id = codeReviewService.addComment(reqDto);
            log.info("comment saved successfully id : {}", id);
            return Response.makeResponse(HttpStatus.OK, "comment saved successfully",1,id);
        } catch (Exception e) {
            return Response.badRequest("fail");
        }
    }

    @PutMapping("/comments/{codeCommentId}")
    public ResponseEntity<?> likeComment(@PathVariable Long codeCommentId) {
        log.info("codeCommentId : {}", codeCommentId);

        CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();
        reqDto.setCodeCommentId(codeCommentId);
        log.info("request Dto : {}", reqDto);

        try {
            final int likeCount = codeReviewService.likeComment(reqDto);

            Map<String, Integer> result = new HashMap<>();
            result.put("likeCount", likeCount);

//            List<Map<?, ?>> list = new ArrayList<>();
//            list.add(result);
            return Response.makeResponse(
            		HttpStatus.OK, "OK",
            		result.size(),result);
        } catch (NoSuchElementException e) {
            return Response.noContent("no content");
        } catch (Exception e) {
            return Response.badRequest("fail");
        }
    }

    @GetMapping("/comments")
    // key : title, contents, writer (포스트 작성자 nickname)
    public ResponseEntity<?> getMyComments(@RequestParam String key, @RequestParam String word,
                                           @PageableDefault(size = 20, sort = "createdDate",
                                                   direction = Sort.Direction.DESC) Pageable pageable) {

        final Page<CodeCommentResDto> response = codeReviewService.getMyComments(key, word, pageable);
        List<Page<?>> list = new ArrayList<>();
        list.add(response);
        return Response.makeResponse(HttpStatus.OK, "OK",list.size(),list);
    }

	/*
	 * public ResponseEntity<?> badRequest() { return new ResponseEntity<>(
	 * ResponseDto.create(HttpStatus.BAD_REQUEST, "bad request",
	 * Collections.emptyList()), HttpStatus.BAD_REQUEST); }
	 * 
	 * public ResponseEntity<?> noElement() { return new ResponseEntity<>(
	 * ResponseDto.create(HttpStatus.NO_CONTENT, "no such element",
	 * Collections.emptyList()), HttpStatus.NO_CONTENT); }
	 */
    @GetMapping("")
    public ResponseEntity<?> codeReviewList(
            @RequestParam String key, @RequestParam(defaultValue = "0") String word,
            @PageableDefault(size = 20) Pageable pageable)
            throws Exception {

        HttpStatus status = HttpStatus.OK;

        log.info("key : {}, word : {}, pageable : {}", key, word, pageable);

        try {
            Page<?> list = codeReviewService.codeReviewSearch(key, word, pageable);
            log.info("list : {}", list);

            return Response.makeResponse(status, "success to get CodeReviewList", list.getSize(), list);
        } catch (Exception e) {
            return Response.badRequest("fail to get CodeReviewList");
        }
    }

    @GetMapping("/{codeReviewId}/history/{versionNum}")
    public ResponseEntity<?> codeHistoryDetail(
            @PathVariable int codeReviewId, @PathVariable int versionNum, HttpServletRequest request,
            @PageableDefault(size = 10) Pageable pageable) {

        HttpStatus status = HttpStatus.OK;

        log.info("codeReviewId : {}, versionNum : {}", codeReviewId, versionNum);
        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        long userId = 2;
        log.info("userId : {}", userId);
        try {
            CodeHistoryDetailDto codeHistory = codeReviewService.codeReviewHistoryDetail(codeReviewId, versionNum, userId, pageable);
            log.info("codeHistory : {}", codeHistory);

            return Response.makeResponse(status, "success to get CodeHistoryDetail", 1, codeHistory);
        } catch (NoSuchElementException s) {
            return Response.noContent("no content History");
        } catch (Exception e) {
            return Response.badRequest("fail to get CodeHistoryDetail");
        }

    }

    @GetMapping("/history/{historyId}/comments")
    public ResponseEntity<?> codeHistoryComments(
            @PathVariable long historyId, HttpServletRequest request,
            @PageableDefault(size = 20, sort = "modifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {

        HttpStatus status = HttpStatus.OK;

        request.getHeader("access-token");
        long userId = 2;
        try {
            Page<CodeCommentDetailDto> list = codeReviewService.codeReviewComments(historyId, userId, pageable);

            return Response.makeResponse(status, "success to get comments", list.getSize(), list);
        } catch (Exception e) {
            return Response.badRequest("fail to get comments");
        }
    }

    @GetMapping("/history/{historyId}/comments/{startLine}")
    public ResponseEntity<?> codeHistoryCommentsDetail(
            @PathVariable long historyId, @PathVariable int startLine, HttpServletRequest request,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable) {

        HttpStatus status = HttpStatus.OK;

        log.info("historyId : {}, startLine : {}", historyId, startLine);


        // access-token에서 userId 뽑기
        String token = request.getHeader("access-token");

        long userId = 2;
        log.info("userId : {}", userId);
        try {
            Page<CodeCommentDetailDto> comments = codeReviewService.codeReviewCommentsDetail(historyId, startLine, userId, pageable);
            log.info("comments : {}", comments);

            return Response.makeResponse(status, "success to get comments detail", comments.getSize(), comments);
        } catch (Exception e) {
            return Response.badRequest("fail to get comments detail");
        }

    }

    @GetMapping("/tag/{word}")
    public ResponseEntity<?> codeReviewRelativeTags(@PathVariable String word) {

        HttpStatus status = HttpStatus.OK;
        log.info("word : {}", word);

        try {
            List<String> tags = codeReviewService.getRelativeTags(word);
            log.info("tags : {}", tags);

            return Response.makeResponse(status, "success to get relative tags", tags.size(), tags);
        } catch (Exception e) {
            return Response.badRequest("fail to get relative tags");
        }
    }
}

package com.project.owlback.url.controller;

import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.dto.UrlReviewDto;
import com.project.owlback.url.service.UrlService;
import com.project.owlback.user.model.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class UrlController {
    private final UserRepository userRepository;
    private final UrlService urlService;

    @PostMapping("")
    public ResponseEntity<?> createUrl(@RequestBody UrlPostDto urlPostDto){

        // 등록하려는 url글 내용
        log.info("url log={}", urlPostDto);
        urlService.createUrl(urlPostDto);

        return ResponseEntity.ok("URL 글 등록 완료");
    }

    @PostMapping("{urlId}/reviews")
    public ResponseEntity<?> createUrlReview(@PathVariable Long urlId, @RequestBody UrlReviewDto urlReviewDto){

        // 등록하려는 url review 내용
        log.info("urlReview log={}", urlReviewDto);
        urlService.createUrlReview(urlId, urlReviewDto);

        return ResponseEntity.ok("URL 리뷰 등록 완료");

    }

    @PostMapping("reviews/{urlReviewId}")
    public ResponseEntity<?> toggleUrlReviewLike(@PathVariable Long urlReviewId){

        // 좋아요(등록/해제) 클릭한 url Id
        log.info("urlReviewId log = {} ", urlReviewId);
        urlService.toggleUrlReviewLike(urlReviewId);

        return ResponseEntity.ok("URL 리뷰 좋아요 toggle 완료");

    }


}

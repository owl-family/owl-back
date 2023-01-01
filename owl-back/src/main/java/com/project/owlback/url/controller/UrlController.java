package com.project.owlback.url.controller;

import com.project.owlback.url.dto.UrlGetDto;
import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.dto.UrlReviewDto;
import com.project.owlback.url.dto.UrlReviewGetDto;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.service.UrlService;
import com.project.owlback.user.model.User;
import com.project.owlback.user.repository.UserRepository;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.util.Calendar;
import java.util.List;

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

    /*
    실시간 -> 최근 게시물 순
    daily,weekly,month -> 지금부터 1,7,30일 전 최신순
    */
    @GetMapping("{condition}")
    public ResponseEntity<?> getUrl(@PathVariable String condition){
        Long a = 1l;
        List<UrlGetDto> uriList = urlService.getUrl(condition,a);
        log.info("urlList ={}",uriList);
        return new ResponseEntity<List<UrlGetDto>>(uriList,HttpStatus.OK);
    }

    @GetMapping("{urlId}/reviews")
    public  ResponseEntity<?> getUrlReview(@PathVariable("urlId") Long urlId){
        log.info("uriId = {}",urlId);
        List<UrlReviewGetDto> urlReviewList = urlService.getUrlReview(urlId);
        log.info("result = {}",urlReviewList);
        return new ResponseEntity<List<UrlReviewGetDto>>(urlReviewList,HttpStatus.OK);
    }

    @GetMapping("search/{word}")
    public ResponseEntity<?> searchUrl(@PathVariable("word") String word){
        log.info("word : {}",word);
        List<UrlGetDto> urlList = urlService.searchUrl(word);

        return new ResponseEntity<List<UrlGetDto>>(urlList,HttpStatus.OK);
    }
}

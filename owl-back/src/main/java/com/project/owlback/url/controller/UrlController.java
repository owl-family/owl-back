package com.project.owlback.url.controller;

import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("")
    public ResponseEntity<?> createUrl(@RequestBody UrlPostDto urlPostDto){

        // 등록하려는 url글 정보
        log.info("url log={}", urlPostDto);
        urlService.createUrl(urlPostDto);

        return ResponseEntity.ok("success");


    }


}

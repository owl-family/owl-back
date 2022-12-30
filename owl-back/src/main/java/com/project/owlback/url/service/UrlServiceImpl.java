package com.project.owlback.url.service;

import com.project.owlback.codereview.repository.TagRepository;
import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    final private TagRepository tagRepository;
    final private UrlRepository urlRepository;

    @Override
    @Transactional
    public void createUrl(UrlPostDto urlPostDto) {

        // 로그인한 user정보
        Url url = Url.builder()
                .content(urlPostDto.getContent())
                .title(urlPostDto.getTitle())
                .link(urlPostDto.getLink())
                .view(0)
                .build();

        urlRepository.save(url);

    }
}

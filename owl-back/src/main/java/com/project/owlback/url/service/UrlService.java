package com.project.owlback.url.service;

import com.project.owlback.codereview.model.Tag;
import com.project.owlback.url.dto.UrlGetDto;
import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.dto.UrlReviewDto;
import com.project.owlback.url.dto.UrlReviewGetDto;
import com.project.owlback.url.model.Url;

import java.util.List;

public interface UrlService {
    void createUrl(UrlPostDto urlPostDto);


    void createUrlTag(Url url, List<Tag> tag);

    void createUrlReview(Long urlId,UrlReviewDto urlReviewDto);


    void toggleUrlReviewLike(Long urlReviewId);

    List<UrlGetDto> getUrl(String condition,Long id);

    List<UrlReviewGetDto> getUrlReview(Long urlId);

    List<UrlGetDto> searchUrl(String word);
}

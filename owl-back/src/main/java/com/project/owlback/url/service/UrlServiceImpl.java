package com.project.owlback.url.service;

import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.TagRepository;
import com.project.owlback.url.dto.UrlGetDto;
import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.dto.UrlReviewDto;
import com.project.owlback.url.dto.UrlReviewGetDto;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.model.UrlLike;
import com.project.owlback.url.model.UrlReview;
import com.project.owlback.url.model.UrlTag;
import com.project.owlback.url.repository.UrlLikeRepository;
import com.project.owlback.url.repository.UrlRepository;
import com.project.owlback.url.repository.UrlReviewRepository;
import com.project.owlback.url.repository.UrlTagRepository;
import com.project.owlback.user.model.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlLikeRepository urlLikeRepository;
    private final UserRepository userRepository;
    private final UrlTagRepository urlTagRepository;

    final private TagRepository tagRepository;
    final private UrlRepository urlRepository;

    final private UrlReviewRepository urlReviewRepository;

    @Override
    @Transactional
    public void createUrl(UrlPostDto urlPostDto) {

        // 로그인한 user정보
        User user = userRepository.findById(urlPostDto.getUserId()).orElseThrow();

        Url url = Url.builder()
                .content(urlPostDto.getContent())
                .title(urlPostDto.getTitle())
                .link(urlPostDto.getLink())
                .view(0)
                .user(user)
                .build();

        urlRepository.save(url);
        createUrlTag(url, urlPostDto.getTag());
    }

    @Override
    @Transactional
    public void createUrlTag(Url url, List<Tag> tag) {
        log.info("info1 log={}", tag);

        for (Tag t : tag) {
            // 없는 Tag이면 Tag 새로 생성하고 UrlTag에 추가.
            if (!tagRepository.existsByContent(t.getContent())) {
                t.setCount(1);
                Tag newTag = tagRepository.save(t);
                urlTagRepository.save(UrlTag.builder()
                        .url(url)
                        .tag(newTag)
                        .build());
            } else { // 있는 Tag이면 그 Tag와 매핑해서 UrlTag에 추가
                tagRepository.CountUp(t.getContent());
                Tag oldTag = tagRepository.findByContent(t.getContent());
                urlTagRepository.save(UrlTag.builder()
                        .url(url)
                        .tag(oldTag)
                        .build());
            }
        }

    }

    @Override
    @Transactional
    public void createUrlReview(Long urlId, UrlReviewDto urlReviewDto) {

        //로그인한 User
        User user = userRepository.findById(urlReviewDto.getUserId()).orElseThrow();
        //후기 등록하려는 URL 글
        Url url = urlRepository.findById(urlId).orElseThrow();

        log.info("user: {}", user);
        log.info("url: {}", url);
        UrlReview urlReview = UrlReview.builder().
                user(user)
                .url(url)
                .startScore(urlReviewDto.getStartScore())
                .content(urlReviewDto.getContent())
                .likeCount(0)
                .build();

        urlReviewRepository.save(urlReview);

//        for(UrlReview ur : url.getReviews()){
//            System.out.println("ur.getContent() = " + ur.getContent());
//        }
    }

    @Override
    @Transactional
    public void toggleUrlReviewLike(Long urlReviewId) {
        Long userId= 3L; //로그인한 유저 임시 등록
        User user = userRepository.findById(userId).orElseThrow();
        log.info("user log = {}", user);

        UrlReview urlReview = urlReviewRepository.findById(urlReviewId).orElseThrow();
        log.info("urlReview log = {}", urlReview);


        List<UrlLike> urlLikeList = urlLikeRepository.existsByUserIdAndReviewId(user.getUserId(), urlReviewId);
        // 좋아요 등록하는 경우
        if(urlLikeList.isEmpty()){
            urlReview.like();
            UrlLike urlLike = UrlLike.builder()
                    .urlReview(urlReview)
                    .user(user)
                    .build();
            urlLikeRepository.save(urlLike);

        }else { //좋아요 해제하는 경우
            UrlLike urlLike = urlLikeList.get(0);
            urlReview.dislike();
            urlLikeRepository.delete(urlLike); // 레포지토리에서 삭제

        }

    }

    @Override
    @Transactional
    public List<UrlGetDto> getUrl(String condition) {
        log.info("condition = {}",condition);
        List<Url> urlList = null;
        switch (condition){
            case "realtime" -> urlList = urlRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));
            case "daily" -> urlList = urlRepository.findByAllTime(1);
            case "weekly" -> urlList = urlRepository.findByAllTime(7);
            case "monthly" -> urlList = urlRepository.findByAllTime(30);
        }
        log.info("urlList = {}",urlList);
        List<UrlGetDto> urlGetDtoList = urlList.stream()
                .map(UrlGetDto::fromEntity).collect(Collectors.toList());
        addTag(urlGetDtoList);
        return urlGetDtoList;
    }

    @Override
    @Transactional
    public List<UrlReviewGetDto> getUrlReview(Long urlId) {
        log.info("urlid = {}",urlId);
        Url url = urlRepository.findById(urlId).orElseThrow();
        log.info("url ={}",url);
        List<UrlReview> urlReviewList = urlReviewRepository.findByUrlId(url.getUrlId()).orElseThrow();
        log.info("UrlReview = {}",urlReviewList);
        List<UrlReviewGetDto> urlReviewGetDtoList = urlReviewList.stream()
                .map(UrlReviewGetDto::fromEntity).collect(Collectors.toList());
        log.info("UrlReviewGetDto ={}",urlReviewGetDtoList);
        return urlReviewGetDtoList;
    }

    @Override
    @Transactional
    public List<UrlGetDto> searchUrl(String word) {
        HashSet<Url> url = new HashSet<>();

        List<Url> searchByUrlTitle = urlRepository.findByTitleContaining(word);
        log.info("searchByUrlTitle = {}",searchByUrlTitle);
        url.addAll(searchByUrlTitle);

        // tag 검색
        Tag tag = tagRepository.findByContent(word);
        log.info("tag ={}",tag);
        List<UrlTag> searchedTag = urlTagRepository.findByTagId(tag.getId());
        log.info("searchedTag = {}",searchedTag);
        List<Url> x = searchedTag.stream().map(UrlTag::getUrl).collect(Collectors.toList());
        url.addAll(x);
        log.info("searched = {}",url);
        List<UrlGetDto> result = url.stream().map(UrlGetDto::fromEntity).collect(Collectors.toList());

        addTag(result);
        result.sort(new Comparator<UrlGetDto>() {
            @Override
            public int compare(UrlGetDto o1, UrlGetDto o2) {
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            }
        });
        log.info("result ={}",result);
        return result;
    }
    public List<UrlGetDto> addTag(List<UrlGetDto> urlGetDto){
        for (int i = 0; i < urlGetDto.size(); i++) {
            UrlGetDto temp = urlGetDto.get(i);
            List<UrlTag> tagList = urlTagRepository.findByUrl(Url.builder().urlId(temp.getUrlId()).build());
            urlGetDto.get(i).setTag(tagList.stream().map(a->a.getTag()).collect(Collectors.toList()));
        }
        return urlGetDto;
    }

}

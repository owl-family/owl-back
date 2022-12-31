package com.project.owlback.url.service;

import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.TagRepository;
import com.project.owlback.url.dto.UrlPostDto;
import com.project.owlback.url.dto.UrlReviewDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Url url = Url.builder()
                .content(urlPostDto.getContent())
                .title(urlPostDto.getTitle())
                .link(urlPostDto.getLink())
                .view(0)
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


}

package com.project.owlback.url.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.owlback.url.model.BaseTimeEntity;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.model.UrlReview;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(of = {"urlReviewId"
        ,"url","user","startScore","content","likeCount","createdDate","modifiedDate"
})
public class UrlReviewGetDto {

    private Long urlReviewId;
    private Url url;

    private User user;

    private Long startScore;

    private String content;

    private Integer likeCount;

    private Instant createdDate;

    private Instant modifiedDate;

    public static UrlReviewGetDto fromEntity(UrlReview urlReview){
        return UrlReviewGetDto.builder()
                .urlReviewId(urlReview.getUrlReviewId())
                .url(urlReview.getUrl())
                .user(urlReview.getUser())
                .startScore(urlReview.getStartScore())
                .content(urlReview.getContent())
                .likeCount(urlReview.getLikeCount())
                .createdDate(urlReview.getCreatedDate())
                .modifiedDate(urlReview.getModifiedDate())
                .build();
    }
}

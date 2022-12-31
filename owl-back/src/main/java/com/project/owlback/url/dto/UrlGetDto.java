package com.project.owlback.url.dto;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.model.UrlReview;
import com.project.owlback.user.model.User;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of={"urlId", "title", "content","link", "view", "user", "favorite", "reviews" })
public class UrlGetDto {
    private long urlId;

    private String title;

    private String content;

    private String link;

    private long view;

    private User user;

    private Instant createdDate;

    private Instant modifiedDate;

    public static UrlGetDto fromEntity(Url url) {
        return UrlGetDto.builder()
                .urlId(url.getUrlId())
                .content(url.getContent())
                .link(url.getLink())
                .title(url.getTitle())
                .view(url.getView())
                .user(url.getUser())
                .createdDate(url.getCreatedDate())
                .modifiedDate(url.getModifiedDate())
                .build();
    }
}
package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeReviewTag;
import com.project.owlback.codereview.repository.CodeReviewTagRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewItemDto {
    int id;
    List<String> tags;
    String title;
    int versionCount;
    String nickname;
    String language;
    int viewCount;
    int commentCount;
    Instant createDate;

    public CodeReviewItemDto(CodeReview c, List<String> tags) throws SQLException {
        this.id = c.getId();
        this.versionCount = c.getVersionCount();
        this.title = c.getTitle();
        this.nickname = c.getWriter().getNickname();
        this.language = c.getCodeLanguage().getDescription();
        this.viewCount = c.getViewCount();
        this.commentCount = c.getCommentCount();
        this.createDate = c.getCreatedDate();
        this.tags = tags;
    }
}

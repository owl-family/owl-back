package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeReview;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;


@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewResDto {
    long id;
    List<String> tags;
    String title;
    String subtitle;
    int versionCount;
    String nickname;
    String language;
    int viewCount;
    int commentCount;
    Instant createDate;

    public CodeReviewResDto(CodeReview c, String subtitle, List<String> tags) throws SQLException {
        this.id = c.getId();
        this.versionCount = c.getVersionCount();
        this.title = c.getTitle();
        this.subtitle = subtitle;
        this.nickname = c.getWriter().getNickname();
        this.language = c.getCodeLanguage().getDescription();
        this.viewCount = c.getViewCount();
        this.commentCount = c.getCommentCount();
        this.createDate = c.getCreatedDate();
        this.tags = tags;
    }
}

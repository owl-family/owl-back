package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeHistoryResDto {
    long id;
    List<String> tags;
    String title;
    String subtitle;
    int versionNum;
    String nickname;
    String language;
    int viewCount;
    int commentCount;
    Instant createDate;

    public CodeHistoryResDto(CodeHistory h, List<String> tags) throws SQLException {
        this.id = h.getId();
        this.versionNum = h.getVersionNum();
        this.title = h.getCodeReview().getTitle();
        this.subtitle = h.getSubTitle();
        this.nickname = h.getCodeReview().getWriter().getNickname();
        this.language = h.getCodeReview().getCodeLanguage().getDescription();
        this.viewCount = h.getCodeReview().getViewCount();
        this.commentCount = h.getCommentCount();
        this.createDate = h.getCreatedDate();
        this.tags = tags;
    }
}

package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeHistory;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeHistoryDetailDto {
    long id;
    String title;
    String subTitle;
    int version;
    Instant createdDate;
    int like;
    String code;
    String contents;
    Page<CodeCommentDetailDto> comments;

    public CodeHistoryDetailDto(CodeHistory history, Page<CodeCommentDetailDto> comments) {
        this.id = history.getId();
        this.title = history.getCodeReview().getTitle();
        this.subTitle = history.getSubTitle();
        this.version = history.getVersionNum();
        this.createdDate = history.getCreatedDate();
        this.like = history.getLike();
        this.code = history.getCode();
        this.contents = history.getContents();
        this.comments = comments;

    }

}

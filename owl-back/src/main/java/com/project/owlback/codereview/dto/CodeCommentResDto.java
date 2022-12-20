package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeComment;
import lombok.Data;

import java.time.Instant;

@Data
public class CodeCommentResDto {
    int codeCommentId;
    int codeReviewId;
    int versionNumber;
    String title;
    String contents;
    Integer likeCount;
    String writer;
    Instant createdDate;

    public CodeCommentResDto(CodeComment comment) {
        this.codeCommentId = comment.getId();
        this.codeReviewId = comment.getCodeHistory().getCodeReview().getId();
        this.versionNumber = comment.getCodeHistory().getVersionNum();
        this.title = comment.getCodeHistory().getCodeReview().getTitle();
        this.contents = comment.getContents();
        this.likeCount = comment.getLikeCount();
        this.writer = comment.getCodeHistory().getCodeReview().getWriter().getNickname();
        this.createdDate = comment.getCreatedDate();
    }
}

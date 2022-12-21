package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.repository.CodeCommentLikeRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CodeCommentDetailDto {
    String writer;
    String contents;
    int startLine;
    int endLine;
    int parent;
    int depth;
    int likeCount;
    Instant createdDate;
    boolean like;

    public CodeCommentDetailDto(CodeComment comment, boolean isLike) throws SQLException {
        this.writer = comment.getWriter().getNickname();
        this.contents = comment.getContents();
        this.startLine = comment.getStartLine();
        this.endLine = comment.getEndLine();
        this.parent = comment.getParent();
        this.depth = comment.getDepth();
        this.likeCount = comment.getLikeCount();
        this.createdDate = comment.getCreatedDate();
        this.like = isLike;

    }
}

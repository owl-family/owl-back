package com.project.owlback.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewCommentReqDto {
    private Integer codeHistoryId;
    private Integer parent;
    private Integer depth;
    private String contents;
    private Integer startLine;
    private Integer endLine;
    private Integer codeReviewId;
    private Integer versionNum;
    private Integer codeCommentId;
    private boolean like;
}

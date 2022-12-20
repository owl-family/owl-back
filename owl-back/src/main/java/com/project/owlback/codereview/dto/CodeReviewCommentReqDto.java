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
    private Long codeHistoryId;
    private Long parent;
    private Integer depth;
    private String contents;
    private Integer startLine;
    private Integer endLine;
    private Long codeReviewId;
    private Integer versionNum;
    private Long codeCommentId;
    private boolean like;
}

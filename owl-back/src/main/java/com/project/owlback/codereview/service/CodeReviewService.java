package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;

public interface CodeReviewService {
    Integer addComment(CodeReviewCommentReqDto reqDto);
    int likeComment(CodeReviewCommentReqDto reqDto);
}

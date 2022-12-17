package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;

public interface CodeReviewService {
    void addComment(CodeReviewCommentReqDto reqDto);
    void likeComment(CodeReviewCommentReqDto reqDto);
}

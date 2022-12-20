package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeReviewService {
    Long addComment(CodeReviewCommentReqDto reqDto);
    int likeComment(CodeReviewCommentReqDto reqDto);

    Page<CodeCommentResDto> getMyComments(String key, String word, Pageable pageable);
}

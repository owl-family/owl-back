package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeReviewService {

    Page<?> codeReviewSearch(String key, String word, Pageable pageable) throws Exception;

    CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, long userId, Pageable pageable) throws Exception;

    Page<CodeCommentDetailDto> codeReviewCommentsDetail(long historyId, int startLine, long userId, Pageable pageable) throws Exception;

    List<String> getRelativeTags(String word) throws Exception;

    Page<CodeCommentDetailDto> codeReviewComments(long historyId, long userId, Pageable pageable) throws Exception;
}

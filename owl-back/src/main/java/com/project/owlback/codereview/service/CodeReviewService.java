package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface CodeReviewService {
    Page<CodeReviewItemDto> codeReviewList(String key, int id, Pageable pageable) throws Exception;

    Page<CodeReviewItemDto> codeReviewSearch(String key, String word, Pageable pageable) throws Exception;

    CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId, Pageable pageable) throws Exception;

    Page<CodeCommentDetailDto> codeReviewCommentsDetail(int historyId, int startLine, int userId, Pageable pageable) throws Exception;

    List<String> getRelativeTags(String word) throws Exception;

    Page<CodeCommentDetailDto> codeReviewComments(int historyId, int userId, Pageable pageable) throws Exception;
}

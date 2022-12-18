package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.model.Tag;

import java.util.List;

public interface CodeReviewService {
    List<CodeReviewItemDto> codeReviewList(String key, int id) throws Exception;

    List<CodeReviewItemDto> codeReviewSearch(String key, String word) throws Exception;

    CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId) throws Exception;

    List<CodeCommentDetailDto> codeReviewCommentsDetail(int historyId, int startLine, int userId) throws Exception;

    List<String> getRelativeTags(String word) throws Exception;
}

package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;

import java.util.List;

public interface CodeReviewService {
    List<CodeReviewItemDto> codeReviewList(String key, int id);

    List<CodeReviewItemDto> codeReviewSearch(String key, String word);

    CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId);
}

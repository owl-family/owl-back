package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;

import java.util.List;

public interface CodeReveiwService {
    List<CodeReviewDto> codeReviewList(String key, int id);

    List<CodeReviewDto> codeReviewSearch(String key, String word);

    CodeHistoryDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId);
}

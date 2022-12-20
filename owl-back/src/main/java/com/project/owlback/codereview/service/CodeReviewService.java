package com.project.owlback.codereview.service;

import java.util.List;

import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;

public interface CodeReviewService {
	Long create(CodeReviewDto codeReviewDto);
	
	void createCodeReviewTag(CodeReview codeReview, List<Tag> tag);
	
	List<Tag> createTag(List<Tag> tag);
	
	Long createHistory(CodeHistory codeHistory);
	
	CodeHistory setCodeReviewToCodeHistory(CodeHistory codeHistory, Long id) throws Exception;
	
	List<CodeHistoryDto> getCodeReviewHistory(Long id) throws Exception;
}

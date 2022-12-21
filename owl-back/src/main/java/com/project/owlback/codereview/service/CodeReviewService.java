package com.project.owlback.codereview.service;

import java.util.List;

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;

public interface CodeReviewService {
	Long create(CodeReviewPostDto codeReview);
	
	void createCodeHistoryTag(CodeHistory codeHistory, List<Tag> tag);
	
	List<Tag> createTag(List<Tag> tag);
	
	Long createHistory(CodeHistory codeHistory,List<Tag> tag);
	
	CodeHistory setCodeReviewToCodeHistory(CodeHistoryPostDto codeHistoryPostDto, Long id) throws Exception;
	
	List<CodeHistoryGetDto> getCodeReviewHistory(Long id) throws Exception;
}

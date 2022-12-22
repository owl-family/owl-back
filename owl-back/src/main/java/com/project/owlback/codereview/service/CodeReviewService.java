package com.project.owlback.codereview.service;
import java.util.List; 

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeReviewService {
	
	Long create(CodeReviewPostDto codeReview);
	
	void createCodeHistoryTag(CodeHistory codeHistory, List<Tag> tag);
	
	List<Tag> createTag(List<Tag> tag);
	
	Long createHistory(CodeHistory codeHistory,List<Tag> tag);
	
	CodeHistory setCodeReviewToCodeHistory(CodeHistoryPostDto codeHistoryPostDto, Long id) throws Exception;
	
	List<CodeHistoryGetDto> getCodeReviewHistory(Long id) throws Exception;
	
    Page<?> codeReviewSearch(String key, String word, Pageable pageable) throws Exception;

    CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, long userId, Pageable pageable) throws Exception;

    Page<CodeCommentDetailDto> codeReviewCommentsDetail(long historyId, int startLine, long userId, Pageable pageable) throws Exception;

    List<String> getRelativeTags(String word) throws Exception;

    Page<CodeCommentDetailDto> codeReviewComments(long historyId, long userId, Pageable pageable) throws Exception;

    Long addComment(CodeReviewCommentReqDto reqDto);
    int likeComment(CodeReviewCommentReqDto reqDto);

    Page<CodeCommentResDto> getMyComments(String key, String word, Pageable pageable);
}

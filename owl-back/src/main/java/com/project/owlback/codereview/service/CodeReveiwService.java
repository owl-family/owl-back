package com.project.owlback.codereview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.CodeRevieHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
public class CodeReveiwService {
	@Autowired
	CodeReviewRepository codeReviewRepository;
	@Autowired
	CodeRevieHistoryRepository codeReviewHistoryRepository;
	@Transactional
	public void create(CodeReviewDto codeReviewDto) {
		// TODO Auto-generated method stub
		// user 정보
		// codereview 정보
		CodeReview codeReview = CodeReview.builder()
				.versionCount(codeReviewDto.getVersionCount())
				.title(codeReviewDto.getTitle())
				.writer(codeReviewDto.getWriter())
				.studyGroup(codeReviewDto.getStudyGroup())
				.codeScope(codeReviewDto.getCodeScope())
				.codeLanguage(codeReviewDto.getCodeLanguage())
				.build();
		codeReviewRepository.save(codeReview);
		
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReview)
				.code(codeReviewDto.getCodeHistory().getCode())
				.subTitle(codeReviewDto.getCodeHistory().getSubTitle())
				.contents(codeReviewDto.getCodeHistory().getContents())
				.versionNum(codeReviewDto.getCodeHistory().getVersionNum())
				.like(0)
				.commentCount(0)
				.build();

		createHistory(codeHistory);
	}

	public void createHistory(CodeHistory codeHistory) {
		// TODO Auto-generated method stub
		codeReviewHistoryRepository.save(codeHistory);
	}
	
	public CodeHistory setCodeReviewToCodeHistory(CodeHistory codeHistory, int id) {
		System.out.println("enter");
		codeHistory = CodeHistory.builder()
				.codeReview(codeReviewRepository.findById(id))
				.code(codeHistory.getCode())
				.subTitle(codeHistory.getSubTitle())
				.contents(codeHistory.getContents())
				.versionNum(codeHistory.getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		System.out.println(codeHistory.toString());
		return codeHistory;
	}
	
	public List<CodeHistory> getCodeReviewHistory(int id) {
		return codeReviewHistoryRepository.findByCodeReview(codeReviewRepository.findById(id));
	}
}

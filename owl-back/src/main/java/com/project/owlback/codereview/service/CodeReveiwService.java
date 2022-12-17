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
	CodeReviewRepository codereviewRepository;
	@Autowired
	CodeRevieHistoryRepository codereviewhistoryRepository;
	@Transactional
	public void create(CodeReviewDto codeReviewDto) {
		// TODO Auto-generated method stub
		// user 정보
		// codereview 정보
		System.out.println("도착");
		System.out.println("codereview =>"+codeReviewDto.toString());
		CodeReview codereview = new CodeReview();
		codereview.setVersionCount(codeReviewDto.getVersionCount());
		codereview.setTitle(codeReviewDto.getTitle());
		// user 정보
		codereview.setWriter(codeReviewDto.getWriter());
		codereview.setStudyGroup(codeReviewDto.getStudyGroup());
		codereview.setCodeScope(codeReviewDto.getCodeScope());
		codereview.setCodeLanguage(codeReviewDto.getCodeLanguage());
				
		codereviewRepository.save(codereview);
		CodeHistory codehistory = new CodeHistory();
		codehistory.setCodeReview(codereview);
		codehistory.setCode(codeReviewDto.getCodeHistory().getCode());
		codehistory.setSubTitle(codeReviewDto.getCodeHistory().getSubTitle());
		codehistory.setContents(codeReviewDto.getCodeHistory().getContents());
		codehistory.setVersionNum(codeReviewDto.getCodeHistory().getVersionNum());
		createHistory(codehistory);
	}

	public void createHistory(CodeHistory codehistory) {
		// TODO Auto-generated method stub
		codereviewhistoryRepository.save(codehistory);
	}
	
	public CodeHistory setCodeReviewToCodeHistory(CodeHistory codehistory, int id) {
		CodeReview codereview = codereviewRepository.findById(id);
		codehistory.setCodeReview(codereview);
		return codehistory;
	}
	
	public List<CodeHistory> getCodeReviewHistory(int id) {
		return codereviewhistoryRepository.findByCodeReview(codereviewRepository.findById(id));
	}
}

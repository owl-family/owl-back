package com.project.owlback.codereview.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

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
	public Integer create(CodeReview codeReview) {
		// TODO Auto-generated method stub
		// user 정보
		// codereview 정보
		System.out.println("service");
		System.out.println(codeReview.toString());
		codereviewRepository.save(codeReview);
		
		return codeReview.getId();
	}

	public void createHistory(CodeHistory codehistory) {
		// TODO Auto-generated method stub
		codereviewhistoryRepository.save(codehistory);
	}
}

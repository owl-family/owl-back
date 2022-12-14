package com.project.owlback.codereview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.CodeReviewRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
public class CodeReveiwService {
	@Autowired
	CodeReviewRepo codereviewRepository;
	
	@Transactional
	public void create(CodeReview codeReview) {
		// TODO Auto-generated method stub
		// user 정보
		// codereview 정보
		codereviewRepository.save(codeReview);
	}
}

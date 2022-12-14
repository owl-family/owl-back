package com.project.owlback.codereview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.service.CodeReveiwService;

import lombok.RequiredArgsConstructor;

@RestController
public class CodeReviewController {
	@Autowired
	CodeReveiwService codereveiwService;
	
	@PostMapping("/api/codereviews")
	public ResponseEntity create(@RequestBody CodeReview codeReview) {
		// user 정보
		// codereview 정보
		System.out.println("도착");
		System.out.println("codereview =>"+codeReview.getTitle());
		codereveiwService.create(codeReview);
		// codereviewhistory 정보
		return ResponseEntity.ok("sucess");
	}
	
}

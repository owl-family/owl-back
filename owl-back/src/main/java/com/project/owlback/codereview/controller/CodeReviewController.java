package com.project.owlback.codereview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.CodeRevieHistoryRepository;
import com.project.owlback.codereview.service.CodeReveiwService;

import lombok.RequiredArgsConstructor;

@RestController
public class CodeReviewController {
	@Autowired
	CodeReveiwService codereveiwService;

	@PostMapping("/api/codereviews")
	public ResponseEntity create(@RequestBody CodeReviewDto codeReviewDto) {
		// codereview 정보

		codereveiwService.create(codeReviewDto);

		return ResponseEntity.ok("succes");
	}
	
	@PostMapping("/api/codereviews/{id}/history")
	public ResponseEntity createHistory(@RequestBody CodeHistory codehistory, @PathVariable Integer id) {
		System.out.println("id=>"+id);
		System.out.println(codehistory.toString());
		codereveiwService.createHistory(codereveiwService.setCodeReviewToCodeHistory(codehistory,id));
		return ResponseEntity.ok("succes");
	}
	
	@GetMapping("/api/codereviews/{codeReviewId}/history")
	public ResponseEntity<List<CodeHistory>> getCodeReviewHistory(@PathVariable Integer codeReviewId){
		System.out.println(codeReviewId);
		return new ResponseEntity<List<CodeHistory>>(codereveiwService.getCodeReviewHistory(codeReviewId),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

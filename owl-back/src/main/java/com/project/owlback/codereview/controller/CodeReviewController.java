package com.project.owlback.codereview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.CodeRevieHistoryRepository;
import com.project.owlback.codereview.service.CodeReveiwService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@EnableJpaAuditing
@RequiredArgsConstructor
@RestController
@Slf4j
public class CodeReviewController {
	private final CodeReveiwService codeReveiwService;

	@PostMapping("/api/codereviews")
	public ResponseEntity create(@RequestBody CodeReviewDto codeReviewDto) {
		// codereview 정보
		log.info("info log={}", codeReviewDto);
		codeReveiwService.create(codeReviewDto);

		return ResponseEntity.ok("succes");
	}
	
	@PostMapping("/api/codereviews/{id}/history")
	public ResponseEntity createHistory(@RequestBody CodeHistory codeHistory, @PathVariable Integer id) {
		log.info("info log={}", codeHistory);
		log.info("info log={}",id);
		codeReveiwService.createHistory(codeReveiwService.setCodeReviewToCodeHistory(codeHistory,id));
		return ResponseEntity.ok("succes");
	}
	
	@GetMapping("/api/codereviews/{codeReviewId}/history")
	public ResponseEntity<List<CodeHistoryDto>> getCodeReviewHistory(@PathVariable Integer codeReviewId) throws Exception{
		log.info("info log={}", codeReviewId);
		List<CodeHistoryDto> codeHistoryList = codeReveiwService.getCodeReviewHistory(codeReviewId);
		log.info("info log={}", codeHistoryList);
		return new ResponseEntity<List<CodeHistoryDto>>(codeHistoryList,HttpStatus.OK);
	}
}

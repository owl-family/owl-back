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

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.service.CodeReviewServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@EnableJpaAuditing
@RequiredArgsConstructor
@RestController
@Slf4j
public class CodeReviewController {
	private final CodeReviewServiceImpl codeReviewService;

	@PostMapping("/api/codereviews")
	public ResponseEntity create(@RequestBody CodeReviewPostDto codeReviewPostDto) {
		// codereview 정보
		log.info("info log={}", codeReviewPostDto);
		codeReviewService.create(codeReviewPostDto);

		return ResponseEntity.ok("success");
	}
	
	@PostMapping("/api/codereviews/{id}/history")
	public ResponseEntity createHistory(@RequestBody CodeHistoryPostDto codeHistoryPostDto, @PathVariable Long id) throws Exception {
		log.info("info log={}", codeHistoryPostDto);
		log.info("info log={}",id);
		codeReviewService.createHistory(codeReviewService.setCodeReviewToCodeHistory(codeHistoryPostDto,id),codeHistoryPostDto.getTag());
		return ResponseEntity.ok("success");
	}
	
	@GetMapping("/api/codereviews/{codeReviewId}/history")
	public ResponseEntity<List<CodeHistoryGetDto>> getCodeReviewHistory(@PathVariable Long codeReviewId) throws Exception{
		log.info("info log={}", codeReviewId);
		List<CodeHistoryGetDto> codeHistoryList = codeReviewService.getCodeReviewHistory(codeReviewId);
		log.info("info log={}", codeHistoryList);
		return new ResponseEntity<List<CodeHistoryGetDto>>(codeHistoryList,HttpStatus.OK);
	}
}

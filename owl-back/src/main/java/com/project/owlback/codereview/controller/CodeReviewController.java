package com.project.owlback.codereview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
				
		Integer id = codereveiwService.create(codereview);
		System.out.println("해당 코드리뷰 id : "+id);
		System.out.println("subtitle =>"+codeReviewDto.getCodeHistory().getSubTitle());
		System.out.println("version =>"+ codeReviewDto.getCodeHistory().getVersionNum());
		// codereviewhistory 정보
		CodeHistory codehistory = new CodeHistory();
		codehistory.setCodeReview(codereview);
		codehistory.setCode(codeReviewDto.getCodeHistory().getCode());
		codehistory.setSubTitle(codeReviewDto.getCodeHistory().getSubTitle());
		codehistory.setContents(codeReviewDto.getCodeHistory().getContents());
		codehistory.setVersionNum(codeReviewDto.getCodeHistory().getVersionNum());
		codereveiwService.createHistory(codehistory);
		return ResponseEntity.ok("succes");
	}
	@PostMapping("/api/codereviews/{id}/history")
	public ResponseEntity createHistory(@RequestBody CodeHistory codehistory, @PathVariable Integer id) {
		System.out.println("id=>"+id);
		System.out.println(codehistory.toString());
		return ResponseEntity.ok("succes");
	}
}

package com.project.owlback.codereview.service;

import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeReviewTag;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.repository.CodeReviewTagRepository;
import com.project.owlback.codereview.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
public class CodeReviewServiceImpl implements CodeReviewService{
	final private CodeReviewRepository codeReviewRepository;
	
	final private CodeReviewHistoryRepository codeReviewHistoryRepository;
	
	final private CodeReviewTagRepository codeReviewTagRepository;
	
	final private TagRepository tagRepository;
	

	@Transactional
	public Long create(CodeReviewDto codeReviewDto) {

		log.info("info log={}", codeReviewDto);
		
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
		log.info("info log={}", codeReview);
		
		codeReviewRepository.save(codeReview);
		
		log.info("info log={}", codeReview);
		
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReview)
				.code(codeReviewDto.getCodeHistory().getCode())
				.subTitle(codeReviewDto.getCodeHistory().getSubTitle())
				.contents(codeReviewDto.getCodeHistory().getContents())
				.versionNum(codeReviewDto.getCodeHistory().getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		log.info("info log={}",codeHistory);
		
		createHistory(codeHistory);
		
		log.info("info log={}",codeHistory);
		if(codeReviewDto.getTag() != null) {
			codeReviewDto.setTag(createTag(codeReviewDto.getTag()));
			createCodeReviewTag(codeReview, codeReviewDto.getTag());
		}
		
		log.info("info log={}", codeReviewDto);
		
		return codeReview.getId();
	}
	
	@Transactional
	public void createCodeReviewTag(CodeReview codeReview, List<Tag> tag) {
		
		log.info("info log={}", tag);
		tag.stream()
		.forEach(x->codeReviewTagRepository.save(CodeReviewTag.builder()
				.count(0)
				.codeReview(codeReview)
				.tag(x)
				.build()));
		log.info("info log={}", tag);
	}
	
	@Transactional
	public List<Tag> createTag(List<Tag> tag) {
		
//		tag.stream()
//		.filter((x->!tagRepository.existsByContent(x.getContent())))
//		.forEach(x->tagRepository.save(x));
		
		for (int i = 0; i < tag.size(); i++) {
			// createtag
			tag.get(i).setCount(0);
			if(!tagRepository.existsByContent(tag.get(i).getContent())) {
				tagRepository.save(tag.get(i));
			}else {
				tag.set(i,tagRepository.findByContent(tag.get(i).getContent()));
			}
		}
		return tag;
	}
	public Long createHistory(CodeHistory codeHistory) {
		// TODO Auto-generated method stub
		log.info("info log={}", codeHistory);
		codeReviewHistoryRepository.save(codeHistory);
		return codeHistory.getId();
	}
	
	public CodeHistory setCodeReviewToCodeHistory(CodeHistory codeHistory, Long id) throws Exception{
		codeHistory = CodeHistory.builder()
				.codeReview(codeReviewRepository.findById(id).orElseThrow())
				.code(codeHistory.getCode())
				.subTitle(codeHistory.getSubTitle())
				.contents(codeHistory.getContents())
				.versionNum(codeHistory.getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		return codeHistory;
	}
	
	public List<CodeHistoryDto> getCodeReviewHistory(Long id) throws Exception{
//		CodeReview optionCodeReview = codeReviewRepository.findById(id);
//		===> []
		
		CodeReview optionCodeReview = codeReviewRepository.findById(id).orElseThrow();
//		===> NoSuchElementException
		log.info("info log={}", optionCodeReview);
		return codeReviewHistoryRepository.findByCodeReview(optionCodeReview).stream()
				.map(CodeHistoryDto::fromEntity).collect(Collectors.toList());
	}
}

package com.project.owlback.codereview.service;

import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeHistoryTag;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.repository.CodeHistoryTagRepository;
import com.project.owlback.codereview.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
public class CodeReviewServiceImpl implements CodeReviewService{
	final private CodeReviewRepository codeReviewRepository;
	
	final private CodeReviewHistoryRepository codeReviewHistoryRepository;
	
	final private CodeHistoryTagRepository codeHistoryTagRepository;
	
	final private TagRepository tagRepository;
	

	@Transactional
	public Long create(CodeReviewPostDto codeReviewPostDto) {

		log.info("info log={}", codeReviewPostDto);
		
		// user 정보
		// codereview 정보
		CodeReview codeReview = CodeReview.builder()
				.versionCount(1)
				.title(codeReviewPostDto.getTitle())
				.writer(codeReviewPostDto.getWriter())
				.studyGroup(codeReviewPostDto.getStudyGroup())
				.codeScope(codeReviewPostDto.getCodeScope())
				.codeLanguage(codeReviewPostDto.getCodeLanguage())
				.build();
		log.info("info log={}", codeReview);
		
		codeReviewRepository.save(codeReview);
		
		log.info("info log={}", codeReview);
		
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReview)
				.code(codeReviewPostDto.getCodeHistoryPostDto().getCode())
				.subTitle(codeReviewPostDto.getCodeHistoryPostDto().getSubTitle())
				.contents(codeReviewPostDto.getCodeHistoryPostDto().getContents())
				.versionNum(codeReviewPostDto.getCodeHistoryPostDto().getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		log.info("info log={}",codeHistory);
		
		createHistory(codeHistory,codeReviewPostDto.getCodeHistoryPostDto().getTag());
		
		log.info("info log={}",codeHistory);
		
		
		log.info("info log={}", codeReviewPostDto);
		
		return codeReview.getId();
	}
	
	@Transactional
	public void createCodeHistoryTag(CodeHistory codeHistory, List<Tag> tag) {
		
		log.info("info log={}", tag);
		tag.stream()
		.forEach(x->codeHistoryTagRepository.save(CodeHistoryTag.builder()
				.count(0)
				.codeHistory(codeHistory)
				.tag(x)
				.build()));
		log.info("info log={}", tag);
	}
	
	@Transactional
	public List<Tag> createTag(List<Tag> tag) {
		
		log.info("info1 log={}",tag);
		for (int i = 0; i < tag.size(); i++) {
			// createtag
			if(!tagRepository.existsByContent(tag.get(i).getContent())) {
				tag.get(i).setCount(0);
				tagRepository.save(tag.get(i));
			}else {
				tagRepository.CountUp(tag.get(i).getContent());
				tag.set(i,tagRepository.findByContent(tag.get(i).getContent()));
			}
		}
		return tag;
	}
	
	@Transactional
	public Long createHistory(CodeHistory codeHistory,List<Tag> tag) {
		// TODO Auto-generated method stub
		log.info("info log={}", codeHistory);
		codeReviewHistoryRepository.save(codeHistory);
		if(tag != null) {
			tag = createTag(tag);
			
			createCodeHistoryTag(codeHistory, tag);
		}
		return codeHistory.getId();
	}
	
	public CodeHistory setCodeReviewToCodeHistory(CodeHistoryPostDto codeHistoryPostDto, Long id) throws Exception{
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReviewRepository.findById(id).orElseThrow())
				.code(codeHistoryPostDto.getCode())
				.subTitle(codeHistoryPostDto.getSubTitle())
				.contents(codeHistoryPostDto.getContents())
				.versionNum(codeHistoryPostDto.getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		return codeHistory;
	}
	
	public List<CodeHistoryGetDto> getCodeReviewHistory(Long id) throws Exception{
//		CodeReview optionCodeReview = codeReviewRepository.findById(id);
//		===> []
		
		CodeReview optionCodeReview = codeReviewRepository.findById(id).orElseThrow();
//		===> NoSuchElementException
		log.info("info log={}", optionCodeReview);
		
		List<CodeHistoryGetDto> codeHistoryGetDto = codeReviewHistoryRepository.findByCodeReview(optionCodeReview).stream()
				.map(CodeHistoryGetDto::fromEntity).collect(Collectors.toList());
		
		for (int i = 0; i < codeHistoryGetDto.size(); i++) {
			CodeHistoryGetDto temp = codeHistoryGetDto.get(i);
			log.info("info log={}", temp);
			List<CodeHistoryTag> tempTag = codeHistoryTagRepository.findByCodeHistory(CodeHistory.builder().id(temp.getId()).build());
			log.info("info log={}", tempTag);
			codeHistoryGetDto.get(i).setTag((List<Tag>) tempTag.stream()
					.map(x->x.getTag()).collect(Collectors.toList()));
		}
		return codeHistoryGetDto;
	}
}

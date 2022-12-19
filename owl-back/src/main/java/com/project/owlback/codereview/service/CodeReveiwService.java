package com.project.owlback.codereview.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeReviewTag;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.CodeRevieHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.repository.CodeReviewTagRepository;
import com.project.owlback.codereview.repository.TagRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeReveiwService {
	final private CodeReviewRepository codeReviewRepository;
	
	final private CodeRevieHistoryRepository codeReviewHistoryRepository;
	
	final private CodeReviewTagRepository codeReviewTagRepository;
	
	final private TagRepository tagRepository;
	

	@Transactional
	public void create(CodeReviewDto codeReviewDto) {
		// TODO Auto-generated method stub
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
		codeReviewRepository.save(codeReview);
		
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReview)
				.code(codeReviewDto.getCodeHistory().getCode())
				.subTitle(codeReviewDto.getCodeHistory().getSubTitle())
				.contents(codeReviewDto.getCodeHistory().getContents())
				.versionNum(codeReviewDto.getCodeHistory().getVersionNum())
				.like(0)
				.commentCount(0)
				.build();

		createHistory(codeHistory);
		
		codeReviewDto.setTag(createTag(codeReviewDto.getTag()));
		createCodeReviewTag(codeReview, codeReviewDto.getTag());
	}
	
	@Transactional
	public void createCodeReviewTag(CodeReview codeReview, List<Tag> tag) {
		tag.stream()
		.forEach(x->codeReviewTagRepository.save(CodeReviewTag.builder()
				.count(0)
				.codeReview(codeReview)
				.tag(x)
				.build()));
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
	public void createHistory(CodeHistory codeHistory) {
		// TODO Auto-generated method stub
		codeReviewHistoryRepository.save(codeHistory);
	}
	
	public CodeHistory setCodeReviewToCodeHistory(CodeHistory codeHistory, int id) {
		codeHistory = CodeHistory.builder()
				.codeReview(codeReviewRepository.findById(id))
				.code(codeHistory.getCode())
				.subTitle(codeHistory.getSubTitle())
				.contents(codeHistory.getContents())
				.versionNum(codeHistory.getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		return codeHistory;
	}
	
	public List<CodeHistory> getCodeReviewHistory(int id) throws Exception{
		Optional<CodeReview> optionCodeReview = Optional.ofNullable(codeReviewRepository.findById(id));
		if(optionCodeReview.isPresent()) {
			return codeReviewHistoryRepository.findByCodeReview(optionCodeReview.get());
		}else {
			return (List<CodeHistory>) new NoSuchElementException("no CodeReview");
		}
	}
}

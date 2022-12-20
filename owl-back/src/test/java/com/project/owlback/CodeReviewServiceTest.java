package com.project.owlback;

import static org.assertj.core.api.Assertions.assertThat; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.codereview.model.StudyCriteria;
import com.project.owlback.codereview.model.StudyGroup;
import com.project.owlback.codereview.model.StudyJoinProcess;
import com.project.owlback.codereview.model.StudyStatus;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.model.User;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.service.CodeReviewServiceImpl;

import lombok.extern.slf4j.Slf4j;
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class CodeReviewServiceTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CodeReviewServiceImpl codeReviewService;
	
	@Autowired
	private CodeReviewRepository codeReviewRepository;
	
	@Autowired
	private CodeReviewHistoryRepository codeReviewHistoryRepository;
	
	@Test
	@DisplayName("코드리뷰_추가")
	public void addCodeReviewTest() throws Exception{
		// input
		User inputUser = User.builder()
				.id(1)
				.nickname("emotion")
				.build();
		CodeReviewDto inputDto = CodeReviewDto.builder()
				.versionCount(1)
				.title("testing")
				.writer(inputUser)
				.commentCount(0)
				.studyGroup(StudyGroup.builder()
						.id(2)
						.name("test")
						.goal("goaltest")
						.studyInformation("infomationTest")
						.maxMember(10)
						.curMember(3)
						.processStudyJoinProcess(StudyJoinProcess.builder()
								.id(1)
								.build())
						.studyCriteria(StudyCriteria.builder()
								.id(1)
								.build())
						.studyStatus(StudyStatus.builder()
								.id(1)
								.build())
						.build())
				.codeScope(CodeScope.builder().id(1).build())
				.codeLanguage(CodeLanguage.builder().id(1).build())
				.codeHistory(CodeHistory.builder()
						.code("hellow")
						.subTitle("subTitle test")
						.contents("contents test")
						.versionNum(1)
						.build())
				.tag(List.of(Tag.builder().content("test1").build(), Tag.builder().content("test2").build()))
				.build();
		Long x = codeReviewService.create(inputDto);
		log.info("Test info log={}",x);
		final Optional<CodeReview> codeReview = codeReviewRepository.findById(x);
		log.info("Test info log={}",codeReview);
		assertThat(codeReview.get());
	}
	
	@Test
	@DisplayName("코드히스토리_추가")
	public void addCodeHistoryTest() throws Exception{
		Long inputId = 15L;
		CodeHistory inputDto = CodeHistory.builder()
				.code("Testing code")
				.subTitle("subTitle test")
				.contents("contents test")
				.versionNum(1)
				.build();
		
		Long x = codeReviewService.createHistory(codeReviewService.setCodeReviewToCodeHistory(inputDto,inputId));
		log.info("Test info log={}",x);
		log.info("Test info log={}",codeReviewHistoryRepository.findById(x));
	}
	
	@Test
	@DisplayName("코드히스토리_상세보기")
	public void getCodeHistoryListTest() throws Exception{
		Long id = 35L;
		List<CodeHistoryDto> codeHistoryList = codeReviewService.getCodeReviewHistory(id);
		log.info("Test info log={}", codeHistoryList);
	}
}

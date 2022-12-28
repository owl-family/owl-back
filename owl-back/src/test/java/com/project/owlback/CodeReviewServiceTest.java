package com.project.owlback;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.owlback.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.studygroup.model.StudyCriteria;
import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.model.StudyJoinProcess;
import com.project.owlback.studygroup.model.StudyStatus;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.service.CodeReviewServiceImpl;


@Transactional
@AutoConfigureMockMvc
@SpringBootTest
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
				.userId(1L)
				.nickname("emotion")
				.build();
		CodeReviewPostDto inputDto = CodeReviewPostDto.builder()
//				.versionCount(1)
				.title("testing")
				.writer(inputUser)
//				.commentCount(0)
				.studyGroup(StudyGroup.builder()
						.studyGroupId(2L)
						.name("test")
						.goal("goaltest")
						.information("infomationTest")
						.maxMember(10)
						.curMember(3)
						.joinProcess(StudyJoinProcess.builder()
								.joinProcessId(1L)
								.build())
						.criteria(StudyCriteria.builder()
								.criteriaId(1L)
								.build())
						.status(StudyStatus.builder()
								.statusId(1L)
								.build())
						.build())
				.codeScope(CodeScope.builder().id(1L).build())
				.codeLanguage(CodeLanguage.builder().id(1L).build())
				.codeHistoryPostDto(CodeHistoryPostDto.builder()
						.code("hellow")
						.subTitle("subTitle test")
						.contents("contents test")
						.versionNum(1)
						.tag(List.of(Tag.builder().content("test1").build(), Tag.builder().content("test2").build()))
						.build())
				.build();
		Long x = codeReviewService.create(inputDto);
		final Optional<CodeReview> codeReview = codeReviewRepository.findById(x);
		assertThat(codeReview.get());
	}
	
	@Test
	@DisplayName("코드히스토리_추가")
	public void addCodeHistoryTest() throws Exception{
		Long inputId = 15L;
		List<Tag> tag = new ArrayList<>();
		CodeHistoryPostDto inputDto = CodeHistoryPostDto.builder()
				.code("Testing code")
				.subTitle("subTitle test")
				.contents("contents test")
				.versionNum(1)
				.tag(tag)
				.build();
		Long x = codeReviewService.createHistory(codeReviewService.setCodeReviewToCodeHistory(inputDto,inputId),tag);
		assertThat(x);
	}
	
	@Test
	@DisplayName("코드히스토리_상세보기")
	public void getCodeHistoryListTest() throws Exception{
		Long id = 35L;
		List<CodeHistoryGetDto> codeHistoryList = codeReviewService.getCodeReviewHistory(id);
		assertThat(codeHistoryList);
	}
}

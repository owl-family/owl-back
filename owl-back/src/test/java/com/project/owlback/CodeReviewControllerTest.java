package com.project.owlback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.codereview.model.StudyGroup;
import com.project.owlback.codereview.model.User;

@SpringBootTest
public class CodeReviewControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void testCodeReview() throws Exception{
		CodeReviewDto test = new CodeReviewDto();
		test.setVersionCount(1);
		test.setTitle("테스트 중입니다");
		User writer = new User();
		writer.setId(1);
		writer.setNickname("아아");
		test.setWriter(writer);
		StudyGroup studyGroup = new StudyGroup();
		studyGroup .setId(2);
		test.setStudyGroup(studyGroup );
		CodeScope codeScope = new CodeScope();
		codeScope .setId(1);
		test.setCodeScope(codeScope );
		CodeLanguage codeLanguage = new CodeLanguage();
		codeLanguage .setId(1);
		test.setCodeLanguage(codeLanguage );
		CodeHistory codeHistory = new CodeHistory();
		codeHistory.setCode("print(hello)");
		codeHistory.setSubTitle("최적화 맞ㄴ나");
		codeHistory.setContents("이거 맞나");
		codeHistory.setVersionNum(2);
		test.setCodeHistory(codeHistory);
		String content = objectMapper.writeValueAsString(test);
		
		String uri = "/api/codereviews";
		mvc.perform(post(uri)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
}

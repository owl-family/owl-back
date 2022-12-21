package com.project.owlback;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableMockMvc
@SpringBootTest
public class CodeReviewControllerTest {
    @Autowired
    private MockMvc mockMvc; // HttpClient와 비슷한 request와 response를 해주는 JUnit 객체

    @Test
    @DisplayName("코드리뷰_목록 API")
    public void CodeReviewListSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews?key=all";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                });

    }

    @Test
    @DisplayName("코드리뷰_히스토리 상세보기 API")
    public void CodeHistoryDetailSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/4/history/1";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                });

    }

    @Test
    @DisplayName("코드리뷰_댓글 상세보기 API")
    public void CodeCommentDetailSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/history/4/comments/2";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                });

    }

    @Test
    @DisplayName("코드리뷰_댓글 조회 API")
    public void CodeCommentsSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/history/4/comments?page=0";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                });

    }

    @Test
    @DisplayName("코드리뷰_관련 태그 API")
    public void CodeReviewRelativeTagsSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/tag/탐색";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                });

    }

}

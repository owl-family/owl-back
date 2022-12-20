package com.project.owlback;

import com.project.owlback.codereview.controller.CodeReviewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    public void codeReviewListSuccess() throws Exception {
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
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeReviewSearchSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/search?key=tag&word=탐색";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeReviewSearchBadRequest() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/search?key=tag";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isBadRequest());

    }

    @Test
    public void codeHistoryDetailSuccess() throws Exception {
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
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeHistoryDetailNoContent() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/2/history/0";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNoContent());

    }

    @Test
    public void codeHistoryDetailNotFound() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/history/0";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNotFound());

    }

    @Test
    public void codeCommentDetailSuccess() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/history/4/comments/1";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeCommentDetailNoContent() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/history/4/comments/0";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNoContent());

    }

    @Test
    public void codeCommentDetailNotFound() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/4/comments/0";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNotFound());

    }

    @Test
    public void codeCommentsSuccess() throws Exception {
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
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeReviewRelativeTagsSuccess() throws Exception {
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
                    System.out.println(response.getContentAsString());
                });

    }

    @Test
    public void codeReviewRelativeTagsNoContent() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/tag/abc";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNoContent());

    }

    @Test
    public void codeReviewRelativeTagsNotFound() throws Exception {
        // given : 테스트를 위해 데이터들을 준비하는 과정
        String url = "/api/codereviews/tag/";
        // when : 실제로 요청을 하거나 실행을 해보는 과정
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                // then : 실행한 결과를 검증하는 과정
                .andExpect(status().isNotFound());

    }
}

package com.project.owlback.codereview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.repository.CodeCommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CodeReviewServiceTest {

    @Autowired
    private CodeReviewService codeReviewService;

    @Autowired
    private CodeCommentRepository codeCommentRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("테스트 동작 확인")
    public void test() throws Exception {
        String message = "test";
        //given

        //when
        
        //then
        assertThat(message)
                .as("Junit5 에서 assertj 를 사용할때 편한점 -> IDE 의 assertions 도움을 받을 수 있다.")
                .isEqualTo("test");
    }

    @Test
    @DisplayName("코드리뷰_댓글_추가")
    public void addComments() throws Exception {
        //given

        String message = "테스트 코맨트";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1)
                .depth(1)
                .contents(message)
                .codeHistoryId(1)
                .build();
        //when
        final Integer id = codeReviewService.addComment(reqDto);

        //then
        final Optional<CodeComment> comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getContents()).isEqualTo(message);
    }

    @Test
    @DisplayName("MVC_코드리뷰_댓글_추가")
    public void addCommentMvc() throws Exception {
        Map<String, String> json = new HashMap<>();
        json.put("codeHistoryId", "1");
        json.put("parent", "1");
        json.put("depth", "1");
        json.put("contents", "comment mvc test");
        json.put("startLine", "1");
        json.put("endLine", "2");

        ObjectMapper objectMapper = new ObjectMapper();

        this.mvc.perform(
                post("/api/codereviews/3/comments")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("코드리뷰_댓글_좋아요")
    public void likeComment() throws Exception {
        //given
        final List<CodeComment> top = codeCommentRepository.findTop1By();
        int id = top.get(0).getId();
        int like = top.get(0).getLikeCount();
        final CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();
        reqDto.setCodeCommentId(id);

        //when
        codeReviewService.likeComment(reqDto);

        //then
        Optional<CodeComment> comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getLikeCount()).isEqualTo(like + 1);

        //when
        codeReviewService.likeComment(reqDto);

        //then
        comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getLikeCount()).isEqualTo(like);
    }
}
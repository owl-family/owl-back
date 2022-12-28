package com.project.owlback.codereview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.user.model.QUser;
import com.project.owlback.codereview.repository.CodeCommentRepository;
import com.project.owlback.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CodeReviewServiceTest {
    @Autowired
    private JPAQueryFactory queryFactory;

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
    @DisplayName("코드리뷰_댓글_추가_성공")
    public void addComments() throws Exception {
        //given

        String message = "테스트 코맨트";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(1L)
                .build();
        //when
        final Long id = codeReviewService.addComment(reqDto);

        //then
        final Optional<CodeComment> comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getContents()).isEqualTo(message);
    }

    @Test
    @DisplayName("코드리뷰_댓글_추가_실패")
    public void addCommentsFail() throws Exception {
        //given

        String message = "테스트 코맨트";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(9999L)
                .build();
        //when

        //then
        assertThatThrownBy(()->codeReviewService.addComment(reqDto))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("MVC_코드리뷰_댓글_추가_성공")
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
    @DisplayName("코드리뷰_댓글_좋아요_성공")
    public void likeComment() throws Exception {
        //given
        final List<CodeComment> top = codeCommentRepository.findTop1By();
        Long id = top.get(0).getId();
        int like = top.get(0).getLikeCount();
        final CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();
        reqDto.setCodeCommentId(id);

        //when
        codeReviewService.likeComment(reqDto);

        //then
        Optional<CodeComment> comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getLikeCount()).isEqualTo(like+1);

        //when
        codeReviewService.likeComment(reqDto);

        //then
        comment = codeCommentRepository.findById(id);
        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getLikeCount()).isEqualTo(like);
    }

    @Test
    @DisplayName("코드리뷰_댓글_좋아요_실패")
    public void likeCommentFail() throws Exception {
        //given
        final List<CodeComment> top = codeCommentRepository.findTop1By();
        int like = top.get(0).getLikeCount();
        final CodeReviewCommentReqDto reqDto = new CodeReviewCommentReqDto();


        //when
        final Long id = Long.MAX_VALUE;

        //then
        Assertions.assertThatThrownBy(()-> {
            reqDto.setCodeCommentId(id);
            codeReviewService.likeComment(reqDto);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("나의_댓글_가져오기_title")
    public void getMyCommentsByTitle() throws Exception {
        //given
        String message = "test comment in Test";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(1L)
                .build();

        final Long id = codeReviewService.addComment(reqDto);

        //when
        final PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<CodeCommentResDto> myComments = codeReviewService.getMyComments("title", "title", pageRequest);

        //then
        assertThat(myComments.getContent().get(0).getTitle()).contains("title");
    }

    @Test
    @Disabled
    @DisplayName("나의_댓글_가져오기_실패(key 값 에러)")
    public void getMyCommentsByTitleFail() throws Exception {
        //given
        String message = "test comment in Test";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(1L)
                .build();

        final Long id = codeReviewService.addComment(reqDto);

        //when
        final PageRequest pageRequest = PageRequest.of(0, 20);

        //then
        assertThatThrownBy(() -> codeReviewService.getMyComments("unknown", "title", pageRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("나의_댓글_가져오기_Contents")
    public void getMyCommentsByContents() throws Exception {
        //given
        String message = "test comment in Test";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(1L)
                .build();

        final Long id = codeReviewService.addComment(reqDto);

        //when
        final PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<CodeCommentResDto> myComments = codeReviewService.getMyComments("contents", "test comment", pageRequest);

        //then
        assertThat(myComments.getContent().get(0).getContents()).isEqualTo(message);
    }

    @Test
    @DisplayName("나의_댓글_가져오기_writer")
    public void getMyCommentsByWriter() throws Exception {
        //given
        String message = "test comment in Test";
        CodeReviewCommentReqDto reqDto = CodeReviewCommentReqDto.builder()
                .startLine(1)
                .endLine(2)
                .parent(1L)
                .depth(1)
                .contents(message)
                .codeHistoryId(1L)
                .build();

        final Long id = codeReviewService.addComment(reqDto);
        final String writer = "ludwings";
        //when
        final PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<CodeCommentResDto> myComments = codeReviewService.getMyComments("writer", writer, pageRequest);

        //then
        assertThat(myComments.getContent().get(0).getWriter()).contains(writer);
    }

    @Test
    @DisplayName("QueryDSL 동작 테스트")
    public void queryDsl() throws Exception {
        final User user = queryFactory.select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.nickname.eq("ludwings"))
                .fetchOne();

        assertThat(user.getNickname()).isEqualTo("ludwings");
    }
}
package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.User;
import com.project.owlback.codereview.repository.CodeCommentRepository;
import com.project.owlback.codereview.repository.CodeHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService{
    private final CodeReviewRepository codeReviewRepository;
    private final UserRepository userRepository;
    private final CodeHistoryRepository codeHistoryRepository;
    private final CodeCommentRepository codeCommentRepository;

    @Override
    @Transactional
    public void addComment(CodeReviewCommentReqDto reqDto) {
        Integer userId = 1; // temporary use before create user function

        final CodeComment comment = CodeComment.builder()
                .startLine(reqDto.getStartLine())
                .endLine(reqDto.getEndLine())
                .parent(reqDto.getParent())
                .depth(reqDto.getDepth())
                .contents(reqDto.getContents())
                .likeCount(0)
                .build();

        final User user = userRepository.findById(userId).orElseThrow();
        final CodeHistory codeHistory = codeHistoryRepository.findById(reqDto.getCodeHistoryId()).orElseThrow();

        log.info("found user : {}", user);
        log.info("found codeHistory : {}", codeHistory);

        comment.setCodeHistory(codeHistory);
        comment.setWriter(user);

        log.info("comment : {}", comment);

        codeCommentRepository.save(comment);
        log.info("comment has been saved successfully");
    }

    @Override
    public void likeComment(CodeReviewCommentReqDto reqDto) {
        Integer userId = 1; // temporary use before create user function

        final CodeComment comment = codeCommentRepository.findById(reqDto.getCodeCommentId()).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();

    }
}

package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeCommentLike;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.User;
import com.project.owlback.codereview.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CodeCommentLikeRepository codeCommentLikeRepository;

    @Override
    @Transactional
    public Integer addComment(CodeReviewCommentReqDto reqDto) {
        Integer userId = 1; // temporary use before create user function

        final CodeComment comment = CodeComment.builder()
                .startLine(reqDto.getStartLine())
                .endLine(reqDto.getEndLine())
                .parent(reqDto.getParent())
                .depth(reqDto.getDepth())
                .contents(reqDto.getContents())
                .likeCount(0)
                .build();

        // 1. JpaAuditing
        // 2. builder 패턴
        // 3. Optional
        // 4. logging -> slf4j -> 공부하면 좋다
        final User user = userRepository.findById(userId).orElseThrow();
        final CodeHistory codeHistory = codeHistoryRepository.findById(reqDto.getCodeHistoryId()).orElseThrow();

        log.info("found user : {}", user);
        log.info("found codeHistory : {}", codeHistory);
        comment.setCodeHistory(codeHistory);
        comment.setWriter(user);

        log.info("comment : {}", comment);

        codeCommentRepository.save(comment);
        log.info("comment has been saved successfully");

        return comment.getId();
    }

    @Override
    @Transactional
    public int likeComment(CodeReviewCommentReqDto reqDto) {
        Integer userId = 1; // temporary use before create user function

        final CodeComment comment = codeCommentRepository.findById(reqDto.getCodeCommentId()).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();

        log.info("comment : {}, user : {}", comment, user);

        final Optional<CodeCommentLike> byCodeCommentAndUser =
                codeCommentLikeRepository.findByCodeCommentAndUser(comment, user);

        byCodeCommentAndUser.ifPresentOrElse(
                codeCommentLike -> {
                    codeCommentLikeRepository.delete(codeCommentLike);
                    comment.dislike();
                },
                () -> {
                    final CodeCommentLike codeCommentLike = CodeCommentLike.builder().codeComment(comment).user(user).build();
                    codeCommentLikeRepository.save(codeCommentLike);
                    comment.like();
                });

        return comment.getLikeCount();
    }

    @Override
    public Page<CodeCommentResDto> getMyComments(String key, String word, Pageable pageable) {

        final int uid = 1; // temporary comment writer's
        final User user = userRepository.findById(uid).get();

        Page<CodeCommentResDto> res = null;// id
        Page<CodeComment> page;

        if(key.equals("title")) {
            // find by code review title
            page = codeCommentRepository.findByWriterAndCodeReviewTitle(user, word, pageable);
            res = page.map(CodeCommentResDto::new);
        } else if(key.equals("contents")) {
            // find by code comment contents
            page = codeCommentRepository.findByWriterAndContentsContains(user, word, pageable);
            res = page.map(CodeCommentResDto::new);
        } else if(key.equals("writer")) {
            // find by code review writer
            page = codeCommentRepository.findByWriterAndUserNickName(user, word, pageable);
            res = page.map(CodeCommentResDto::new);
        }

        return res;
    }
}

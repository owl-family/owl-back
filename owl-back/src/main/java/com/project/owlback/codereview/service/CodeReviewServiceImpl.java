package com.project.owlback.codereview.service;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeReviewTag;
import com.project.owlback.codereview.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {

    private final CodeReviewRepositoryCustom codeReviewRepositoryCustom;

    private final CodeReviewHistoryRepository codeReviewHistoryRepository;

    private final CodeReviewTagRepositoryCustom codeReviewTagRepositoryCustom;

    private final CodeCommentLikeRepository codeCommentLikeRepository;

    private final CodeCommentRepositoryCustom codeCommentRepositoryCustom;

    @Override
    public Page<CodeReviewItemDto> codeReviewSearch(String key, String word, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setKey(key);

        switch (key) {
            case "study" -> condition.setStudyGroupId(Long.parseLong(word));
            case "title" -> condition.setTitle(word.toUpperCase());
            case "writer" -> condition.setWriter(word.toUpperCase());
            case "language" -> condition.setLanguage(word.toUpperCase());
            case "tag" -> condition.setTag(word.toUpperCase());
        }

        Page<CodeReview> list = null;

        if (!key.equals("tag")) {
            list = codeReviewRepositoryCustom.SearchByCondition(condition, pageable);
        } else {
            Page<CodeReviewTag> tagList = codeReviewTagRepositoryCustom.findByTagContent(condition, pageable);
            log.info("get codeReviewTagList : {}", tagList);
            list = tagList.map(CodeReviewTag::getCodeReview);
        }

        log.info("get codeReviewList : {}", list);

        return changeToCodeReviewDto(list);
    }

    @Override
    public CodeHistoryDetailDto codeReviewHistoryDetail(
            int codeReviewId, int versionNum, int userId, Pageable pageable) throws Exception {
        CodeHistory history = codeReviewHistoryRepository.findByCodeReviewIdAndVersionNum(codeReviewId, versionNum).orElseThrow();
        log.info("found history : {}", history);
        CodeHistoryDetailDto result = changeToCodeReviewHistoryDto(history, userId, pageable);
        log.info("change to dto : {}", result);

        return result;
    }

    @Override
    public Page<CodeCommentDetailDto> codeReviewCommentsDetail(
            long historyId, int startLine, int userId, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(historyId);
        condition.setStartLine(startLine);

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public Page<CodeCommentDetailDto> codeReviewComments(long historyId, int userId, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(historyId);

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public List<String> getRelativeTags(String word) throws Exception {
        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setTag(word);

        List<String> tags = codeReviewTagRepositoryCustom.getTagContent(condition);
        log.info("get tags : {}", tags);

        return tags;
    }


    // CodeReviewList -> CodeReviewDtoList
    public Page<CodeReviewItemDto> changeToCodeReviewDto(Page<CodeReview> list) throws Exception {
        Page<CodeReviewItemDto> dtoList = list.map(c -> {
            try {
                CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
                condition.setCodeReviewId(c.getId());
                List<String> tags = codeReviewTagRepositoryCustom.getTagContentbyCodeReviewId(condition);
                log.info("get tagList : {}", tags);

                return new CodeReviewItemDto(c, tags);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("change to dto successfully");
        return dtoList;
    }

    public CodeHistoryDetailDto changeToCodeReviewHistoryDto(
            CodeHistory history, int userId, Pageable pageable) throws Exception {
        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(history.getId());

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("get code comments : {}", comments);

        return new CodeHistoryDetailDto(history, comments);
    }

    public Page<CodeCommentDetailDto> changeToCodeCommentDto(Page<CodeComment> comments, int userId) throws Exception {

        return comments.map(c -> {
            try {
                int cnt = codeCommentLikeRepository.countByUserIdAndCodeCommentId(userId, c.getId());
                boolean isLike = (cnt > 0);
                log.info("get code comment like : {}", isLike);

                return new CodeCommentDetailDto(c, isLike);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

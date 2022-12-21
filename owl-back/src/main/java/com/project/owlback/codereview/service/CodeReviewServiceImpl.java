package com.project.owlback.codereview.service;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryResDto;
import com.project.owlback.codereview.dto.CodeReviewResDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeHistoryTag;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {

    private final CodeReviewRepositoryCustom codeReviewRepositoryCustom;

    private final CodeReviewHistoryRepository codeReviewHistoryRepository;

    private final CodeHistoryTagRepositoryCustom codeHistoryTagRepositoryCustom;

    private final CodeCommentLikeRepository codeCommentLikeRepository;

    private final CodeCommentRepositoryCustom codeCommentRepositoryCustom;

    private final CodeHistoryRepositoryCustom codeHistoryRepositoryCustom;

    @Override
    public Page<?> codeReviewSearch(String key, String word, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setKey(key);

        switch (key) {
            case "study" -> condition.setStudyGroupId(Long.parseLong(word));
            case "title" -> condition.setTitle(word.toUpperCase());
            case "writer" -> condition.setWriter(word.toUpperCase());
            case "language" -> condition.setLanguage(word.toUpperCase());
            case "tag" -> condition.setTag(word.toUpperCase());
        }


        if (!key.equals("tag")) {
            Page<CodeReview> list = null;

            list = codeReviewRepositoryCustom.SearchByCondition(condition, pageable);
            log.info("get codeReviewList : {}", list);

            return changeToCodeReviewDto(list);
        } else {
            Page<CodeHistoryTag> tagList = codeHistoryTagRepositoryCustom.findByTagContent(condition, pageable);
            log.info("get codeReviewTagList : {}", tagList);

            Page<CodeHistory> list = null;

            list = tagList.map(CodeHistoryTag::getCodeHistory);

            return changeToCodeHistoryDto(list);
        }

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

        List<String> tags = codeHistoryTagRepositoryCustom.getTagContent(condition);
        log.info("get tags : {}", tags);

        return tags;
    }


    // CodeReviewList -> CodeReviewDtoList
    public Page<CodeReviewResDto> changeToCodeReviewDto(Page<CodeReview> list) throws Exception {
        Page<CodeReviewResDto> dtoList = list.map(c -> {
            try {
                CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
                condition.setCodeReviewId(c.getId());

                List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeReviewId(c.getId(), c.getVersionCount());
                log.info("get tagList : {}", tags);

                String subtitle = codeHistoryRepositoryCustom.getSubTitle(c.getId(), c.getVersionCount());

                return new CodeReviewResDto(c, subtitle, tags);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("change to dto successfully");
        return dtoList;
    }

    private Page<CodeHistoryResDto> changeToCodeHistoryDto(Page<CodeHistory> list) {
        Page<CodeHistoryResDto> dtoList = list.map(h -> {
            try {
                List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeHistoryId(h.getId());
                log.info("get tagList : {}", tags);

                return new CodeHistoryResDto(h, tags);
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

        List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeHistoryId(history.getId());

        return new CodeHistoryDetailDto(history, tags, comments);
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

package com.project.owlback.codereview.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {
    final private CodeReviewRepository codeReviewRepository;

    final private CodeReviewHistoryRepository codeReviewHistoryRepository;

    final private CodeReviewTagRepository codeReviewTagRepository;

    final private CodeCommentRepository codeCommentRepository;

    final private CodeCommentLikeRepository codeCommentLikeRepository;


    @Override
    public Page<CodeReviewItemDto> codeReviewList(String key, int id, Pageable pageable) throws Exception {
        Page<CodeReview> list = null;

        if (key.equals("all")) {
            list = codeReviewRepository.findAll(pageable);
            log.info("get codeReviewList : {}", list);

        } else if (key.equals("study")) {
            list = codeReviewRepository.findByStudyGroupId(id, pageable);
            log.info("get codeReviewList : {}", list);
        }

        return changeToCodeReviewDto(list);
    }

    @Override
    public Page<CodeReviewItemDto> codeReviewSearch(String key, String word, Pageable pageable) throws Exception {
        Page<CodeReview> list = null;

        if (key.equals("title")) {
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "modifiedDate"));
            list = codeReviewRepository.findByTitleLike('%' + word + '%', pageable);
            log.info("get codeReviewList : {}", list);

        } else if (key.equals("tag")) {
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "modifiedDate"));
            Page<CodeReviewTag> codeReviewTagList = codeReviewTagRepository
                    .findByTagContentLike('%' + word + '%', pageable);
            log.info("get codeReviewTagList : {}", codeReviewTagList);
            list = codeReviewTagList.map(CodeReviewTag::getCodeReview);

            log.info("get codeReviewList : {}", list);

        } else if (key.equals("writer")) {
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "codeReview.modifiedDate"));
            list = codeReviewRepository.findByWriterNicknameLike('%' + word + '%', pageable);
            log.info("get codeReviewList : {}", list);

        } else if (key.equals("language")) {
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "modifiedDate"));
            String param = word.toUpperCase();
            list = codeReviewRepository.findByCodeLanguageDescriptionLike('%' + param + '%', pageable);
            log.info("get codeReviewList : {}", list);

        }

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
            int historyId, int startLine, int userId, Pageable pageable) throws Exception {

        Page<CodeComment> list = codeCommentRepository.findByCodeHistoryIdAndStartLine(historyId, startLine, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public Page<CodeCommentDetailDto> codeReviewComments(int historyId, int userId, Pageable pageable) throws Exception {

        Page<CodeComment> list = codeCommentRepository.findByCodeHistoryId(historyId, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public List<String> getRelativeTags(String word) throws Exception {
        List<CodeReviewTag> tags = codeReviewTagRepository.findByTagContentLikeOrderByCountDesc('%' + word + '%');
        log.info("get tags : {}", tags);

        List<String> result = new ArrayList<>();
        for (CodeReviewTag tag : tags) {
            result.add(tag.getTag().getContent());
        }
        log.info("get tag content : {}", result);

        return result;
    }


    // CodeReviewList -> CodeReviewDtoList
    public Page<CodeReviewItemDto> changeToCodeReviewDto(Page<CodeReview> list) throws Exception {
        Page<CodeReviewItemDto> dtoList = list.map(c -> {
            try {
                List<String> tags = codeReviewTagRepository
                        .findByCodeReviewId(c.getId()).stream().map(t -> t.getTag().getContent()).toList();
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

        Page<CodeComment> list = codeCommentRepository.findByCodeHistoryId(history.getId(), pageable);
        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("get code comments : {}", comments);

        return new CodeHistoryDetailDto(history, comments);
    }

    public Page<CodeCommentDetailDto> changeToCodeCommentDto(Page<CodeComment> comments, int userId) throws Exception {
        Page<CodeCommentDetailDto> list = comments.map(c -> {
            try {
                int cnt = codeCommentLikeRepository.countByUserIdAndCodeCommentId(userId, c.getId());
                boolean isLike = (cnt > 0);
                log.info("get code comment like : {}", isLike);

                return new CodeCommentDetailDto(c, isLike);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return list;
    }

}

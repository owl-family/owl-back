package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeReviewItemDto;
import com.project.owlback.codereview.model.*;
import com.project.owlback.codereview.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeReviewServiceImpl implements CodeReviewService {
    @Autowired
    private CodeReviewRepository codeReviewRepository;

    @Autowired
    private CodeReviewHistoryRepository codeReviewHistoryRepository;

    @Autowired
    private CodeReviewTagRepository codeReviewTagRepository;

    @Autowired
    private CodeCommentRepository codeCommentRepository;

    @Autowired
    private CodeCommentLikeRepository codeCommentLikeRepository;


    @Override
    public List<CodeReviewItemDto> codeReviewList(String key, int id) throws Exception {
        List<CodeReview> list = new ArrayList<>();

        if (key.equals("all")) {
            list = codeReviewRepository.findAll();
        } else if (key.equals("study")) {
            list = codeReviewRepository.findByStudyGroupId(id);
        }

        return changeToCodeReviewDto(list);
    }

    @Override
    public List<CodeReviewItemDto> codeReviewSearch(String key, String word) throws Exception {
        List<CodeReview> list = new ArrayList<>();

        if (key.equals("title")) {
            list = codeReviewRepository.findByTitleLike('%' + word + '%');
            System.out.println(word);
            System.out.println(list.size());
        } else if (key.equals("tag")) {
            List<CodeReviewTag> codeReviewTagList = codeReviewTagRepository.findByTagContentLike('%' + word + '%');
            for (CodeReviewTag codeReviewTag : codeReviewTagList) {
                list.add(codeReviewTag.getCodeReview());
            }
        } else if (key.equals("writer")) {
            list = codeReviewRepository.findByWriterNicknameLike('%' + word + '%');
        } else if (key.equals("language")) {
            String param = word.toUpperCase();
            list = codeReviewRepository.findByCodeLanguageDescriptionLike('%' + param + '%');
        }

        return changeToCodeReviewDto(list);
    }

    @Override
    public CodeHistoryDetailDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId) throws Exception {
        CodeHistory history = codeReviewHistoryRepository.findByCodeReviewIdAndVersionNum(codeReviewId, versionNum);
        CodeHistoryDetailDto result = changeToCodeReviewHistoryDto(history, userId);

        return result;
    }

    @Override
    public List<CodeCommentDetailDto> codeReviewCommentsDetail(int historyId, int startLine, int userId) throws Exception {
        List<CodeComment> list = codeCommentRepository.findByCodeHistoryIdAndStartLine(historyId, startLine);
        List<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        return comments;
    }

    // CodeReviewList -> CodeReviewDtoList
    public List<CodeReviewItemDto> changeToCodeReviewDto(List<CodeReview> list) throws Exception {
        List<CodeReviewItemDto> resultList = new ArrayList<>();
        for (CodeReview item : list) {
            CodeReviewItemDto dto = new CodeReviewItemDto();
            dto.setId(item.getId());
            dto.setTitle(item.getTitle());
            dto.setViewCount(item.getViewCount());
            dto.setNickname(item.getWriter().getNickname());
            dto.setLanguage(item.getCodeLanguage().getDescription());
            dto.setViewCount(item.getViewCount());
            dto.setCommentCount(item.getCommentCount());
            dto.setCreateDate(item.getCreatedDate());

            List<CodeReviewTag> tagList = codeReviewTagRepository.findByCodeReviewId(item.getId());
            List<String> tags = new ArrayList<>();
            for (CodeReviewTag tag : tagList) {
                tags.add(tag.getTag().getContent());
            }
            dto.setTags(tags);
            resultList.add(dto);
        }
        return resultList;
    }

    public CodeHistoryDetailDto changeToCodeReviewHistoryDto(CodeHistory history, int userId) throws Exception {
        CodeHistoryDetailDto result = null;
        if (history != null) {
            result = new CodeHistoryDetailDto();
            result.setId(history.getId());
            result.setTitle(history.getCodeReview().getTitle());
            result.setSubTitle(history.getSubTitle());
            result.setVersion(history.getVersionNum());
            result.setCreatedDate(history.getCreatedDate());
            result.setLike(history.getLike());
            result.setCode(history.getCode());
            result.setContents(history.getContents());
            List<CodeComment> list = codeCommentRepository.findByCodeHistoryId(history.getId());
            List<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
            result.setComments(comments);
        }
        return result;
    }

    public List<CodeCommentDetailDto> changeToCodeCommentDto(List<CodeComment> comments, int userId) throws Exception {
        List<CodeCommentDetailDto> list = new ArrayList<>();
        for (CodeComment comment : comments) {
            CodeCommentDetailDto dto = new CodeCommentDetailDto();
            dto.setWriter(comment.getWriter().getNickname());
            dto.setContents(comment.getContents());
            dto.setStartLine(comment.getStartLine());
            dto.setEndLine(comment.getEndLine());
            dto.setParent(comment.getParent());
            dto.setDepth(comment.getDepth());
            dto.setLikeCount(comment.getLikeCount());
            dto.setCreatedDate(comment.getCreatedDate());
            int isLike = codeCommentLikeRepository.countByUserIdAndCodeCommentId(userId, comment.getId());
            if (isLike > 0) {
                dto.setLike(true);
            } else {
                dto.setLike(false);
            }
            list.add(dto);
        }
        return list;
    }
}

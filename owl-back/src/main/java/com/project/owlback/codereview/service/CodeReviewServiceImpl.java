package com.project.owlback.codereview.service;

import com.project.owlback.codereview.dto.CodeCommentDto;
import com.project.owlback.codereview.dto.CodeHistoryDto;
import com.project.owlback.codereview.dto.CodeReviewDto;
import com.project.owlback.codereview.model.*;
import com.project.owlback.codereview.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeReviewServiceImpl implements CodeReveiwService {
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
    public List<CodeReviewDto> codeReviewList(String key, int id) {
        List<CodeReview> list = new ArrayList<>();

        if (key.equals("all")) {
            list = codeReviewRepository.findAll();
        } else if (key.equals("study")) {
            list = codeReviewRepository.findByStudyGroupId(id);
        }

        return changeToCodeReviewDto(list);
    }

    @Override
    public List<CodeReviewDto> codeReviewSearch(String key, String word) {
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


    // CodeReviewList -> CodeReviewDtoList
    public List<CodeReviewDto> changeToCodeReviewDto(List<CodeReview> list) {
        List<CodeReviewDto> resultList = new ArrayList<>();
        for (CodeReview item : list) {
            CodeReviewDto dto = new CodeReviewDto();
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


    @Override
    public CodeHistoryDto codeReviewHistoryDetail(int codeReviewId, int versionNum, int userId) {
        CodeHistory history = codeReviewHistoryRepository.findByCodeReviewIdAndVersionNum(codeReviewId, versionNum);
        CodeHistoryDto result = null;
        // CodeHistory -> CodeHistoryDto
        if (history != null) {
            result = new CodeHistoryDto();
            result.setId(history.getId());
            result.setTitle(history.getCodeReview().getTitle());
            result.setSubTitle(history.getSubTitle());
            result.setVersion(history.getVersionNum());
            result.setCreatedDate(history.getCreatedDate());
            result.setLike(history.getLike());
            result.setCode(history.getCode());
            result.setContents(history.getContents());
            List<CodeCommentDto> comments = getCodeComments(history.getId(), userId);
            result.setComments(comments);
        }
        return result;
    }

    public List<CodeCommentDto> getCodeComments(int historyId, int userId) {
        List<CodeCommentDto> list = new ArrayList<>();
        List<CodeComment> comments = codeCommentRepository.findByCodeHistoryId(historyId);
        for (CodeComment comment : comments) {
            CodeCommentDto dto = new CodeCommentDto();
            dto.setNickname(comment.getWriter().getNickname());
            dto.setContents(comment.getContents());
            dto.setStartLine(comment.getStartLine());
            dto.setEndLine(comment.getEndLine());
            dto.setParent(comment.getParent());
            dto.setDepth(comment.getDepth());
            dto.setLikeCount(comment.getLikeCount());
            dto.setCreatedDate(comment.getCreatedDate());
            int cnt = codeCommentLikeRepository.countByUserIdAndCodeCommentId(comment.getId(), userId);
            if (cnt > 0) {
                dto.setLike(true);
            } else {
                dto.setLike(false);
            }
            list.add(dto);
        }
        return list;
    }
}

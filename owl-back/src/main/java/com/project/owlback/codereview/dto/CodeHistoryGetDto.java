package com.project.owlback.codereview.dto;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id","versionNum","code","subTitle","createdDate","modifiedDate","contents","like","commentCount","codeReview"})
public class CodeHistoryGetDto{
    private Long id;

    private Integer versionNum;

    private String code;

    private String subTitle;

    private Instant createdDate;
	
    private Instant modifiedDate;
  
    private String contents;

    private Integer like;

    private Integer commentCount;

    private CodeReview codeReview;
    
    private List<Tag> tag;
    
    public static CodeHistoryGetDto fromEntity(CodeHistory codeHistory) {
    	return CodeHistoryGetDto.builder()
    			.id(codeHistory.getId())
    			.versionNum(codeHistory.getVersionNum())
    			.code(codeHistory.getCode())
    			.subTitle(codeHistory.getSubTitle())
    			.createdDate(codeHistory.getCreatedDate())
    			.modifiedDate(codeHistory.getModifiedDate())
    			.contents(codeHistory.getContents())
    			.like(codeHistory.getLike())
    			.commentCount(codeHistory.getCommentCount())
    			.codeReview(codeHistory.getCodeReview())
    			.build();
    }
}
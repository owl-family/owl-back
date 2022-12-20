package com.project.owlback.codereview.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code_history")
@Getter
@Setter
@ToString(of = {"id","versionNum","code","subTitle","createdDate","modifiedDate","contents","like","commentCount","codeReview"})
public class CodeHistoryDto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_history_id", nullable = false)
    private Long id;

	@Column(name = "version_num", nullable = false)
    private Integer versionNum;

    @Lob
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "sub_title", nullable = false, length = 50)
    private String subTitle;

    @Column(name = "created_date")
    private Instant createdDate;
	
    @Column(name = "modified_date")
    private Instant modifiedDate;
  
    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "`like`", nullable = false)
    private Integer like;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_review_id", nullable = false)
    private CodeReview codeReview;
    
    public static CodeHistoryDto fromEntity(CodeHistory codeHistory) {
    	return CodeHistoryDto.builder()
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
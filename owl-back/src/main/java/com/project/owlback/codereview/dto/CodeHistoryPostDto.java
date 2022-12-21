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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id","versionNum","code","subTitle","contents","codeReview"})
public class CodeHistoryPostDto{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "code_history_id", nullable = false)
//    private Long id;

//	@Column(name = "version_num", nullable = false)
    private Integer versionNum;

//    @Lob
//    @Column(name = "code", nullable = false)
    private String code;

//    @Column(name = "sub_title", nullable = false, length = 50)
    private String subTitle;

//    @Lob
//    @Column(name = "contents", nullable = false)
    private String contents;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "code_review_id", nullable = false)
    private CodeReview codeReview;
    
//    @Column(name = "tag",nullable = true)
    private List<Tag> tag;

}
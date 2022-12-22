package com.project.owlback.codereview.model;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code_history")
@Getter
@Setter
@ToString(of = {"id","versionNum","code","subTitle","contents","like","commentCount","codeReview"})

public class CodeHistory extends BaseTimeEntity{
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

    @OneToMany(mappedBy = "codeHistory")
    private List<CodeComment> comments = new ArrayList<>();
}
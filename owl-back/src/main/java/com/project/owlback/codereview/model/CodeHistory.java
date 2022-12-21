package com.project.owlback.codereview.model;

import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "code_history")
@Getter
@Setter
public class CodeHistory {
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

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
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

    @OneToMany(mappedBy = "codeHistory")
    private List<CodeComment> comments = new ArrayList<>();
}
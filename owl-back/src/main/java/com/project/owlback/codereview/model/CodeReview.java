package com.project.owlback.codereview.model;

import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="code_review")
public class CodeReview {

    @Id
    @Column(name = "code_review_id", nullable = false)
    private Long id;

    @Column(name = "version_count", nullable = false)
    private Integer versionCount;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_scope_id", nullable = false)
    private CodeScope codeScope;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_language_id", nullable = false)
    private CodeLanguage codeLanguage;

}

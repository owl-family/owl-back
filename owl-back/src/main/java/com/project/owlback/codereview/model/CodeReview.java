package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="code_review")
public class CodeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_review_id", nullable = false)
    private Integer id;

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

    
    @Override
    public String toString() {
    	return "CodeReview [id=" + id + ", versionCount=" + versionCount + ", title=" + title + ", createdDate="
    			+ createdDate + ", modifiedDate=" + modifiedDate + ", viewCount=" + viewCount + ", commentCount="
    			+ commentCount + ", writer=" + writer + ", studyGroup=" + studyGroup + ", codeScope=" + codeScope
    			+ ", codeLanguage=" + codeLanguage + "]";
    }
}

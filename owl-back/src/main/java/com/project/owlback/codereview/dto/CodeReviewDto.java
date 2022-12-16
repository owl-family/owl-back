package com.project.owlback.codereview.dto;



import java.time.Instant;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.codereview.model.StudyGroup;
import com.project.owlback.codereview.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString(of = {"id","versionCount","title"})
public class CodeReviewDto {
//	@Id
//    @Column(name = "code_review_id", nullable = false)
    private Integer id;

//    @Column(name = "version_count", nullable = false)
    private Integer versionCount;

//    @Column(name = "title", nullable = false, length = 50)
    private String title;

//    @Column(name = "created_date")
    private Instant createdDate;

//    @Column(name = "modified_date")
    private Instant modifiedDate;

//    @Column(name = "view_count")
    private Integer viewCount;

//    @Column(name = "comment_count")
    private Integer commentCount;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "writer", nullable = false)
    private User writer;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "code_scope_id", nullable = false)
    private CodeScope codeScope;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "code_language_id", nullable = false)
    private CodeLanguage codeLanguage;
    
    private CodeHistory codeHistory;
//	@Override
//	public String toString() {
//		return "CodeReviewDto [id=" + id + ", versionCount=" + versionCount + ", title=" + title + ", createdDate="
//				+ createdDate + ", modifiedDate=" + modifiedDate + ", viewCount=" + viewCount + ", commentCount="
//				+ commentCount + ", writer=" + writer + ", studyGroup=" + studyGroup + ", codeScope=" + codeScope
//				+ ", codeLanguage=" + codeLanguage + "]";
//	}
}

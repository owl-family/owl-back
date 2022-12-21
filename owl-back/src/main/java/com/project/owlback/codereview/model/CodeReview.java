package com.project.owlback.codereview.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="code_review")
@ToString(of = {"id","versionCount","title","viewCount","commentCount","writer","studyGroup"})
public class CodeReview extends BaseTimeEntity{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "code_review_id", nullable = false)
    private Long id;

    @Column(name = "version_count", nullable = false)
    private Integer versionCount;

    @Column(name = "title", nullable = false, length = 50)
    private String title;


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

package com.project.owlback.favorite.dto.temp;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.user.dto.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CodeReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codeReviewId;

    @Column(name = "VERSION_COUNT")
    private int versionCount;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

    @Column(name = "STUDY_GROUP_ID")
    private long studyGroupId;

    @Column(name = "CODE_SCOPE_ID")
    private long codeScopeId;

    @Column(name = "CODE_LANGUAGE_ID")
    private long codeLanguageId;

    @Column(name = "COMMENT_COUNT")
    private int commentCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "codeReview")
    private Favorite favorite;
}

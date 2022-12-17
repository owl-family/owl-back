package com.project.owlback.codereview.model;

import jakarta.persistence.*;

@Entity
@Table(name = "code_comment_like")
public class CodeCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_comment_like_id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_comment_id", nullable = false)
    private CodeComment codeComment;
}
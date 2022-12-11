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

    public CodeComment getCodeComment() {
        return codeComment;
    }

    public void setCodeComment(CodeComment codeComment) {
        this.codeComment = codeComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
package com.project.owlback.codereview.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "code_review_tag")
public class CodeReviewTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_review_tag_id", nullable = false)
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_review_id", nullable = false)
    private CodeReview codeReview;
}
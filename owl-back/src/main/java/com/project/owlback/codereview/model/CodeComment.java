package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="code_comment")
public class CodeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_comment_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "start_line", nullable = false)
    private Integer startLine;

    @Column(name = "end_line", nullable = false)
    private Integer endLine;

    @Column(name = "parent", nullable = false)
    private Long parent;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_history_id", nullable = false)
    private CodeHistory codeHistory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer", nullable = false)
    private User writer;
}

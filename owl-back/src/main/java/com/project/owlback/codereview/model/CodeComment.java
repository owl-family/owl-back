package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="code_comment")
public class CodeComment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_comment_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "start_line", nullable = false)
    private Integer startLine;

    @Column(name = "end_line", nullable = false)
    private Integer endLine;

    @Column(name = "parent", nullable = false)
    private Integer parent;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Column(name = "like_count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_history_id", nullable = false)
    private CodeHistory codeHistory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

    public void setWriter(User user) {
        this.writer = user;
        writer.getComments().add(this);
    }

    public void setCodeHistory(CodeHistory codeHistory) {
        this.codeHistory = codeHistory;
        codeHistory.getComments().add(this);
    }
}

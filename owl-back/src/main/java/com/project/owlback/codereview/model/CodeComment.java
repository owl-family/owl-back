package com.project.owlback.codereview.model;

import com.project.owlback.user.model.User;
import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "like_count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_history_id", nullable = false)
    @QueryInit("codeReview.writer")
    private CodeHistory codeHistory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

//    연관관계 매서드
    public void setWriter(User user) {
        this.writer = user;
        writer.getComments().add(this); // builder 로 넣으면 이 부분이 없음
    }

    public void setCodeHistory(CodeHistory codeHistory) {
        this.codeHistory = codeHistory;
        codeHistory.getComments().add(this); // builder 로 넣으면 이 부분이 없음
    }

    public void dislike() {
        this.likeCount --;
    }

    public void like() {
        this.likeCount ++;
    }
}

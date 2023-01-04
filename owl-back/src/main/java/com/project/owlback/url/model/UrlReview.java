package com.project.owlback.url.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="url_review")
public class UrlReview extends BaseTimeEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "url_review_id", nullable = false)
    private Long urlReviewId;

    @ManyToOne(optional = false)
    @JoinColumn(name="url_id", nullable = false)
    private Url url;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;

    @Column(name="start_score", nullable = false)
    private Long startScore;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name = "like_count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;

    public void dislike(){
        this.likeCount--;
    }
    public void like(){
        this.likeCount++;
    }

}

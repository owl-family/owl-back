package com.project.owlback.url.model;

import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="url_like")
public class UrlLike {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "url_like_id", nullable = false)
    private Long urlLikeId;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "url_review_id", nullable = false)
    private UrlReview urlReview;


}

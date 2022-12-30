package com.project.owlback.favorite.dto;

import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.url.model.Url;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteId;

    @OneToOne
    @JoinColumn(name = "url_id")
    private Url url;

    @OneToOne
    @JoinColumn(name = "code_review_id")
    private CodeReview codeReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

package com.project.owlback.favorite.dto;

import com.project.owlback.favorite.dto.temp.CodeReview;
import com.project.owlback.favorite.dto.temp.Url;
import com.project.owlback.user.dto.User;
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

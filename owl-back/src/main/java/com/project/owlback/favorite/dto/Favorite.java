package com.project.owlback.favorite.dto;

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

    @Column(name = "URL_ID")
    private long urlId;

    @Column(name = "CODEREVIEW_ID")
    private long codeReviewId;

    @Column(name = "USER_ID")
    private long userId;
}

package com.project.owlback.url.model;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Url extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long urlId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LINK")
    private String link;

    @Column(name = "VIEW")
    private long view;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "url")
    private Favorite favorite;
}
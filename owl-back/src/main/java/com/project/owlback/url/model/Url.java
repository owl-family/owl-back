package com.project.owlback.url.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(of={"urlId", "title", "content","link", "view" })
public class Url extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id", nullable = false)
    private Long urlId;

    @Column(name = "title")
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

    @OneToMany(mappedBy = "url")
    @Builder.Default
    @JsonIgnore
    private List<UrlReview> reviews = new ArrayList<>();
}
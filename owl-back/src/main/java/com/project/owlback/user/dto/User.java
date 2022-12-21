package com.project.owlback.user.dto;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.temp.CodeReview;
import com.project.owlback.favorite.dto.temp.Url;
import com.project.owlback.goal.dto.Subject;
import com.project.owlback.score.dto.Score;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;
    private String nickname;
    private String name;
    private String imgFile;

    private String introduction;
    private String password;
    private Date createdDate;
    private Date modifiedDate;
    private int status;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
//    private Stat stat;
//
    @OneToMany(mappedBy = "user")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "user")
    private List<Score> scores = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Url> urls = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CodeReview> codeReviews = new ArrayList<>();

    public void updatePassword(String password) {
        this.password = password;
    }
}


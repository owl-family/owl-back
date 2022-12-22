package com.project.owlback.user.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.owlback.goal.dto.Goal;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.util.BaseTimeEntity;
import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.temp.CodeReview;
import com.project.owlback.favorite.dto.temp.Url;
import com.project.owlback.goal.dto.Subject;
import com.project.owlback.score.dto.Score;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="user")
public class User extends BaseTimeEntity  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="nickname", nullable=false)
    private String nickname;

    @Column(name="name", nullable=false)
    private String name;


    @Column(name="introduction", nullable = false)
    @ColumnDefault("''")
    private String introduction;

    @Column(name="password", nullable = false)
    private String password;


    @Column(name="status",nullable = false )
    @ColumnDefault("2")
    private Integer status;

    @OneToOne(mappedBy ="user")
    @JsonManagedReference // 순환참조 방지
    private Goal goal;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="img_id")
    private UserImg userImg;

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

    @OneToMany
    @JoinColumn(name = "user_id") //@JoinColumn을 통해 TEAM_ID를 연관관계의 주인으로 설정
    private List<Role> roles = new ArrayList<>();

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInfo(PutUserInfoReq putUserInfoReq){
        this.introduction=putUserInfoReq.getIntroduction();
        this.nickname= putUserInfoReq.getNickname();
    }

    public void deleteUser(){
        this.status=0;
    }

    public void updateImg(UserImg userImg){
        UserImg u = new UserImg();
        u.setImgId(userImg.getImgId());
        u.setFileName(userImg.getFileName());
        u.setFileOriginalName(userImg.getFileOriginalName());
        u.setFileUrl(userImg.getFileUrl());
        this.userImg=u;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public String getUsername() {
        return Long.toString(userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

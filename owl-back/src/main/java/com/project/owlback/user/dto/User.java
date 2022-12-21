package com.project.owlback.user.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.dto.UpdateGoal;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@DynamicInsert
@Table(name="user")
public class User extends BaseTimeEntity {
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
    @ColumnDefault("2") //int형으로 선언했더니 ColumnDefault적용 안됨.. 그래서 Integer로바꿈
    private Integer status;

    @OneToOne(mappedBy ="user")
    @JsonManagedReference // 순환참조 방지
    private Goal goal;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="img_id")
    private UserImg userImg;

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


}


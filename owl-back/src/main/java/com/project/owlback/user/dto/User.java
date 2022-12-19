package com.project.owlback.user.dto;

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
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    private String email;
    private String nickname;
    private String name;
    private String imgFile;

//    @ColumnDefault(" ")
    private String introduction;
    private String password;

    @ColumnDefault("2")
    private int status;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInfo(UpdateInfo updateinfo){
        this.imgFile = updateinfo.getImgFile();
        this.introduction=updateinfo.getIntroduction();
        this.nickname= updateinfo.getNickname();
    }

    public void deleteUser(){
        this.status=0;
    }
}


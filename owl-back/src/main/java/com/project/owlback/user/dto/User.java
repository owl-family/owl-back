package com.project.owlback.user.dto;

import com.project.owlback.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;
    private String nickname;
    private String name;
    private String imgFile;

    private String introduction;
    private String password;
    private int status;

    public void updatePassword(String password) {
        this.password = password;
    }
}


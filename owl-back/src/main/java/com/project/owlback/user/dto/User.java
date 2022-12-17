package com.project.owlback.user.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    public void updatePassword(String password) {
        this.password = password;
    }
}


package com.project.owlback.user.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable {
    private String name;
    private String nickname;
    private String email;

    public SessionUser(User user){
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
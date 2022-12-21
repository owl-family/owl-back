package com.project.owlback.user.dto.res;


import com.project.owlback.user.dto.UserImg;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserInfoRes {
    private String nickname;
    private String introduction;
    private UserImg userImg;
}

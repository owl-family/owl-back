package com.project.owlback.user.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutUserInfoReq {

    private String nickname;
    private String introduction;
}

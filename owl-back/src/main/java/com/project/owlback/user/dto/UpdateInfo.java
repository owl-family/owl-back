package com.project.owlback.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateInfo {

    private String nickname;
    private String imgFile;

    private String introduction;
}

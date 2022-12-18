package com.project.owlback.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserReq {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
    private int status;
    private String introduction;

}

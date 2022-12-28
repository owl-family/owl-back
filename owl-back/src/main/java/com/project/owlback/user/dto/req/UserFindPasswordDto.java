package com.project.owlback.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFindPasswordDto {
    private String email;
    private String name;
}

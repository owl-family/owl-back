package com.project.owlback.studygroup.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppliedMemberRes {
    private Long userId;
    private String nickname;
    private String imgFile;
    private String joinMessage;
}

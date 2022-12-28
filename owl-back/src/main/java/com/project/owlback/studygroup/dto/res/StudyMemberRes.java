package com.project.owlback.studygroup.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class StudyMemberRes {
    private String nickname;
    private String imgFile;
    private Long memberStatusId;
}

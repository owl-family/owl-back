package com.project.owlback.studygroup.dto.res;

import com.project.owlback.studygroup.dto.StudyTag;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class StudyInfo {
    private Long studyGroupId;
//    private List<StudyTag> studyTags;
    private String name;
    private User user;
    private Date createdDate;
    private int curMember;
}

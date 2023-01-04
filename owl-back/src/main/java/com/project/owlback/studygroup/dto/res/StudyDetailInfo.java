package com.project.owlback.studygroup.dto.res;

import com.project.owlback.studygroup.dto.StudyJoinProcess;
import com.project.owlback.studygroup.dto.StudyTag;
import com.project.owlback.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class StudyDetailInfo {
    private Long studyGroupId;
    private String name;
    private String goal;
    private User user;
    private String studyInformation;
    private Date createdDate;
    private String criteriaDetail;
    private StudyJoinProcess studyJoinProcess;
    private int maxMember;
    private int curMember;
    private List<StudyTag> studyTags;
}

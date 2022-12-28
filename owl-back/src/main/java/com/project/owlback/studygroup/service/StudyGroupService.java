package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.dto.res.StudyMemberRes;

import java.util.ArrayList;
import java.util.Optional;

public interface StudyGroupService {
    Optional<ArrayList<StudyMemberRes>> memberList(Long studyGroupId);
    void expire(Long studyGroupId);
}

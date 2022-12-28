package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.dto.StudyCriteria;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudyGroupService {
    Optional<ArrayList<StudyMemberRes>> members(Long studyGroupId);
    void expire(Long studyGroupId);
    Optional<List<StudyCriteria>> criteria();
}

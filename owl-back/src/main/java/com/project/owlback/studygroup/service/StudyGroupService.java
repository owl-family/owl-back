package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.model.StudyCriteria;
import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;
import com.project.owlback.studygroup.dto.res.AppliedMemberRes;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.model.StudyJoinProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface StudyGroupService {
    long joinRequest(long studyId, long userId, String message);

    boolean isJoinRequest(long studyId, long userId);

    long joinAccept(long studyId, long userId);

    List<String> inviteRequest(long studyId, StudyInviteReqDto reqDto);

    StudyGroup getStudyGroup(long studyId);

    long inviteAccept(long studyId, long userId, String joinCode);

    Optional<ArrayList<StudyMemberRes>> members(Long studyGroupId);

    void expire(Long studyGroupId);

    Optional<List<StudyCriteria>> criteria();

    Optional<List<StudyJoinProcess>> joinProcesses();

    Optional<ArrayList<AppliedMemberRes>> applied(Long studyGroupId);
}

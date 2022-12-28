package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;

import java.util.List;

public interface StudyGroupService {
    long joinRequest(long studyId, long userId, String message);

    boolean isJoinRequest(long studyId, long userId);

    long joinAccept(long studyId, long userId);

    List<String> inviteRequest(long studyId, StudyInviteReqDto reqDto);

    StudyGroup getStudyGroup(long studyId);

    long inviteAccept(long studyId, long userId, String joinCode);
}

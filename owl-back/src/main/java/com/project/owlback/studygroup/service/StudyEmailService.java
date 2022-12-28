package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.model.StudyGroup;

import java.util.List;

public interface StudyEmailService {
    void sendInviteEmail(List<String> email, final StudyGroup studyGroup) throws Exception;
}

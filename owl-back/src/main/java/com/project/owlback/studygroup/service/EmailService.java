package com.project.owlback.studygroup.service;

import com.project.owlback.codereview.model.StudyGroup;

import java.util.List;

public interface EmailService {
    void sendInviteEmail(List<String> email, final StudyGroup studyGroup) throws Exception;
}

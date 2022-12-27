package com.project.owlback.studygroup.repository;

import com.project.owlback.codereview.model.StudyMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberStatusRepository extends JpaRepository<StudyMemberStatus, Long> {
    StudyMemberStatus findByDescription(String description);
}

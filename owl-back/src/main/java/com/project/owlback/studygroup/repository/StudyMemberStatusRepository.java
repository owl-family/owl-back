package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyMemberStatus;
import com.project.owlback.studygroup.dto.StudyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberStatusRepository extends JpaRepository<StudyMemberStatus, Long> {
}

package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.StudyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyStatusRepository extends JpaRepository<StudyStatus, Long> {
}

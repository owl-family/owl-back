package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyCriteria;
import com.project.owlback.studygroup.dto.StudyJoinProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyJoinProcessRepository extends JpaRepository<StudyJoinProcess, Long> {
}

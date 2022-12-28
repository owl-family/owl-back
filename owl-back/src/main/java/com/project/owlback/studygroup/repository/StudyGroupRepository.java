package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
}

package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Optional<StudyGroup> findById(long id);

    Optional<StudyGroup> findByIdAndJoinCode(long studyId, String joinCode);
}

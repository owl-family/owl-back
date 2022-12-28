package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Optional<StudyGroup> findByStudyGroupId(long id);
    Optional<StudyGroup> findByStudyGroupIdAndJoinCode(long studyId, String joinCode);
}

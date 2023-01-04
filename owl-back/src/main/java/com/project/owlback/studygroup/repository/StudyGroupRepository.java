package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.res.StudyDetailInfo;
import com.project.owlback.studygroup.dto.res.StudyInfo;
import com.project.owlback.studygroup.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Optional<StudyGroup> findByStudyGroupId(long id);
    Optional<StudyGroup> findByStudyGroupIdAndJoinCode(long studyId, String joinCode);

    Optional<StudyGroup> findByJoinCode(String joinCode);

    List<StudyInfo> findByUserUserId(Long userId);

    List<StudyInfo> findByNameContainingAndStudyStatusStudyStatusIdNot(String name, int studyStatusId);

    List<StudyInfo> findByUserNameContainingAndStudyStatusStudyStatusIdNot(String name, int studyStatusId);

    Optional<StudyDetailInfo> findByStudyGroupId(Long studyId);
}

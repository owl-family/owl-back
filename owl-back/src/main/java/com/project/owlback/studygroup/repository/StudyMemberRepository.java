package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyGroup;
import com.project.owlback.studygroup.dto.StudyMember;
import com.project.owlback.studygroup.dto.StudyMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyGroup(StudyGroup studyGroup);
    List<StudyMember> findByStudyGroupAndMemberStatus(StudyGroup studyGroup, StudyMemberStatus memberStatus);
}

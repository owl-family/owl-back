package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.model.StudyMember;
import com.project.owlback.studygroup.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyGroup(StudyGroup studyGroup);

    List<StudyMember> findByStudyGroupAndMemberStatus(StudyGroup studyGroup, MemberStatus memberStatus);

    Optional<StudyMember> findByUserEmail(String email);
}

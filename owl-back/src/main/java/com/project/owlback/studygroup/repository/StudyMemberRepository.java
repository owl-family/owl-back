package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyGroup;
import com.project.owlback.studygroup.dto.StudyMember;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyGroup(StudyGroup studyGroup);
}

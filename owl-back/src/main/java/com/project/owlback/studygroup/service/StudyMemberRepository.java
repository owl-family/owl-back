package com.project.owlback.studygroup.service;

import com.project.owlback.codereview.model.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}

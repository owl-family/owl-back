package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberStatusRepository extends JpaRepository<MemberStatus, Long> {
    MemberStatus findByDescription(String description);

}

package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.dto.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
//    Optional<User> findByEmail(@Param("email") String email);
//    Optional<User> findByUserId(@Param("name") String name);
//
//    Optional<User> findByEmailAndName(@Param("email") String email, @Param("name") String name);
//
//    Optional<User> findByNickname(@Param("nickname") String nickname);
}

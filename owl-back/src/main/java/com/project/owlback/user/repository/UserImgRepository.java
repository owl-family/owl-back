package com.project.owlback.user.repository;

import com.project.owlback.user.dto.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImgRepository extends JpaRepository<UserImg, Long> {

    Optional<UserImg> findById(Long id);
}


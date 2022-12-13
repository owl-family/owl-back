package com.project.owlback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.user.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

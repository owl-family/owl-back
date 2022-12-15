package com.project.owlback.user.repository;

import com.project.owlback.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, User> {

}

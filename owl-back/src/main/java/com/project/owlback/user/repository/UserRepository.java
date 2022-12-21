package com.project.owlback.user.repository;

import com.project.owlback.codereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

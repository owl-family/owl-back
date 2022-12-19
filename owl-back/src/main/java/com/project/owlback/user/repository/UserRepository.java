package com.project.owlback.user.repository;

import com.project.owlback.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);


    Optional<User> findByEmailAndName(@Param("email") String email, @Param("name") String name);

    Optional<User> findByNickname(@Param("nickname") String nickname);
}

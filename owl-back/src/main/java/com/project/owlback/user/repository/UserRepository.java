package com.project.owlback.user.repository;

import java.util.Optional;

import com.project.owlback.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findByUserId(@Param("name") String name);

    Optional<User> findByEmailAndName(@Param("email") String email, @Param("name") String name);

    Optional<User> findByNickname(@Param("nickname") String nickname);
}

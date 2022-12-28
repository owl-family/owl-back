package com.project.owlback.user.repository;

import com.project.owlback.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<Role,Long> {


}

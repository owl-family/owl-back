package com.project.owlback.goal.repository;

import com.project.owlback.goal.dto.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}

package com.project.owlback.goal.service;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.dto.UpdateGoal;

public interface GoalService {

    void updateGoal(Long userId, UpdateGoal updateGoal);
    
    Goal findByUserId(Long userId);
}

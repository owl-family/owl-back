package com.project.owlback.goal.service;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.dto.UpdateGoal;
import com.project.owlback.goal.repository.GoalRepository;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    private final UserRepository userRepository;

    @Override
    public Goal findByUserId(Long userId) {
        return goalRepository.findById(userId).orElseThrow();
    }
    @Override
    public void updateGoal(Long userId, UpdateGoal updateGoal) {
        Goal goal = goalRepository.findById(userId).orElseThrow();
        goal.updateGoal(updateGoal);

        User user = userRepository.findById(userId).orElseThrow();
        log.info("user : {}" ,user);

        goalRepository.save(goal);
    }


}

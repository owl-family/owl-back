package com.project.owlback.goal.controller;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.service.GoalService;
import com.project.owlback.goal.dto.UpdateGoal;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;
    private static final int ZERO = 0;



    /**
     * 회원 목표 수정 API
     * [PUT] /api/goals/{user_id}
     */
    @PutMapping("{user_id}")
    public ResponseEntity<?> getGoal(@PathVariable("user_id") Long userId, @RequestBody UpdateGoal updateGoal) {

        goalService.updateGoal(userId, updateGoal);

        return Response.makeResponse(HttpStatus.OK,"회원 목표 수정 완료");
    }

    /**
     * 회원 목표 조회 API
     * [GET] /api/goals/{user_id}
     */
    @GetMapping("{user_id}")
    public ResponseEntity<?> getGoal(@PathVariable("user_id") Long userId) {
        System.out.println("회원 목표 조회 ");

        Goal goal = goalService.findByUserId(userId);

        Object result = new UpdateGoal(goal.getSubjectId(),goal.getStudyContent(), goal.getWeeklyCommitment(),
                goal.getDailyGoalTime(),goal.getWeeklyGoalTime());

        return Response.makeResponse(HttpStatus.OK, "회원 목표 조회 완료", 1,result);


    }
}

package com.project.owlback.goal.controller;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.service.GoalService;
import com.project.owlback.user.dto.ResponseDto;
import com.project.owlback.goal.dto.UpdateGoal;
import com.project.owlback.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<ResponseDto> getGoal(@PathVariable("user_id") Long userId, @RequestBody UpdateGoal updateGoal) {

        Goal goal = goalService.findByUserId(userId);
        goalService.updateGoal(userId, updateGoal);

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원 목표 수정 완료")
                .result(Collections.emptyList())
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    /**
     * 회원 목표 조회 API
     * [GET] /api/goals/{user_id}
     */
    @GetMapping("{user_id}")
    public ResponseEntity<ResponseDto> getGoal(@PathVariable("user_id") Long userId) {

        Goal goal = goalService.findByUserId(userId);

        List<Object> goalList = new ArrayList<>();
        goalList.add(new UpdateGoal(goal.getSubjectId(), goal.getStudyContent(), goal.getWeeklyCommitment(),
                goal.getDailyGoalTime(), goal.getWeeklyGoalTime()));

        ResponseDto responseDto = ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원 목표 조회 완료")
                .result(goalList)
                .count(ZERO)
                .build();

        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }
}

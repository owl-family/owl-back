package com.project.owlback.goal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateGoal {

    private Long subjectId;
    private String studyContent;
    private String weeklyCommitment;
    private Integer dailyGoalTime;
    private Integer weeklyGoalTime;
}

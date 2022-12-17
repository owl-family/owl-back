package com.project.owlback.goal.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId;

    @Column(name = "SUBJECT_ID")
    private long subjectId;

    @Column(name = "STUDY_CONTENT")
    private String studyContent;

    @Column(name = "WEEKLY_COMMITMENT")
    private String weeklyCommitment;

    @Column(name = "DAILY_GOAL_TIME")
    private int dailyGoalTime;

    @Column(name = "WEEKLY_GOAL_TIME")
    private int weeklyGoalTime;
}

package com.project.owlback.goal.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.owlback.user.dto.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude="user")
@DynamicInsert
@Table(name="user_goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goal_id", nullable=false)
    private Long goalId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "study_content", nullable=false)
    @ColumnDefault("''")
    private String studyContent;


    @Column(name = "weekly_commitment", nullable=false)
    @ColumnDefault("''")
    private String weeklyCommitment;


    @Column(name = "daily_goal_time", nullable=false)
    @ColumnDefault("0")
    private Integer dailyGoalTime;

    @Column(name = "weekly_goal_time", nullable=false)
    @ColumnDefault("0")
    private Integer weeklyGoalTime;

    @OneToOne
    @JoinColumn(name="user_id")
    @JsonBackReference // 순환참조 방지
    private User user;

    public void updateGoal(UpdateGoal updateGoal){
        this.subjectId=updateGoal.getSubjectId();
        this.studyContent=updateGoal.getStudyContent();
        this.weeklyCommitment=updateGoal.getWeeklyCommitment();
        this.dailyGoalTime=updateGoal.getDailyGoalTime();
        this.weeklyGoalTime=updateGoal.getWeeklyGoalTime();

    }

}

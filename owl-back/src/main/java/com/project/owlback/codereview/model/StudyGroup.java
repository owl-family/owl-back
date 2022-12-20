package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "study_group")
@Getter
@Setter
@Builder
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "goal", nullable = false, length = 1000)
    private String goal;

    @Column(name = "study_information", nullable = false, length = 200)
    private String studyInformation;
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "max_member", nullable = false)
    private Integer maxMember;

    @Column(name = "cur_member", nullable = false)
    private Integer curMember;

    @Column(name = "criteria_detail", length = 10)
    private String criteriaDetail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "process_study_join_process_id", nullable = false)
    private StudyJoinProcess processStudyJoinProcess;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_criteria_id", nullable = false)
    private StudyCriteria studyCriteria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_status_id", nullable = false)
    private StudyStatus studyStatus;

}
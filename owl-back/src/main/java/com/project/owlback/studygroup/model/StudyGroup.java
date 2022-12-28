package com.project.owlback.studygroup.model;

import com.project.owlback.user.model.User;
import com.project.owlback.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study_group")
public class StudyGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id", nullable = false)
    private Long studyGroupId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "goal", nullable = false, length = 1000)
    private String goal;

    @Column(name = "information", nullable = false, length = 200)
    private String information;

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
    @JoinColumn(name = "join_process_id", nullable = false)
    private StudyJoinProcess joinProcess;

    @ManyToOne(optional = false)
    @JoinColumn(name = "criteria_id", nullable = false)
    private StudyCriteria criteria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private StudyStatus status;

    @Column(name = "join_code", nullable = false)
    private String joinCode;

    @OneToMany
    @JoinColumn(name = "study_group_id")
    private List<StudyTag> tags = new ArrayList<>();

    public void expire(StudyStatus status) {
        this.status = status;
    }

}
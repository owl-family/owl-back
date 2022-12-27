package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "joinMessage", "studyGroup", "studyMemberStatus", "user"})
@Table(name = "study_member")
public class StudyMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id", nullable = false)
    private Long id;

    @Column(name = "join_message", nullable = false, length = 100)
    private String joinMessage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_member_status_id", nullable = false)
    private StudyMemberStatus studyMemberStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

package com.project.owlback.studygroup.model;

import com.project.owlback.codereview.model.BaseTimeEntity;
import com.project.owlback.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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
    private Long memberId;

    @Column(name = "join_message", nullable = false, length = 100)
    private String joinMessage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_member_status_id", nullable = false)
    private MemberStatus memberStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

package com.project.owlback.studygroup.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.owlback.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "study_member")
public class StudyMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "join_message")
    private String joinMessage;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="member_status_id")
    private StudyMemberStatus memberStatus;
}

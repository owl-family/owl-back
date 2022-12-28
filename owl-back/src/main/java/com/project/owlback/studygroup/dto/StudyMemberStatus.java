package com.project.owlback.studygroup.dto;

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
@Table(name="study_member_status")
// 가입완료/신청중/초대중
public class StudyMemberStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_status_id", nullable = false)
    private Long memberStatusId;

    @Column(name = "description")
    private String description;
}

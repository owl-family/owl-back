package com.project.owlback.studygroup.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "study_member_status")
// 가입완료/승인대기/초대중
public class MemberStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_status_id", nullable = false)
    private Long memberStatusId;

    @Column(name = "description", nullable = false, length = 45)
    private String description;
}
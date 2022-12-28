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
@Table(name="study_status")
// 활성화된 스터디/종료된 스터디
public class StudyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Column(name = "description")
    private String description;
}

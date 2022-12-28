package com.project.owlback.studygroup.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="study_join_process")
// 직접가입/가입 불가/승인 후 가입
public class StudyJoinProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_process_id", nullable = false)
    private Long joinProcessId;

    @Column(name = "description")
    private String description;
}

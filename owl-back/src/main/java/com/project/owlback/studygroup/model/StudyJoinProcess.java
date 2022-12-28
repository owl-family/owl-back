package com.project.owlback.studygroup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "study_join_process")
public class StudyJoinProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_join_process_id", nullable = false)
    private Long joinProcessId;

    @Column(name = "description", nullable = false, length = 20)
    private String description;
}
package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_join_process")
public class StudyJoinProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_join_process_id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;
}
package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_status")
public class StudyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_status_id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;
}
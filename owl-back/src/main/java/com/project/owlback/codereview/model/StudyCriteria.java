package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_criteria")
public class StudyCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_criteria_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;
}
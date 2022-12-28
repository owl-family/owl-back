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
@Table(name = "study_criteria")
public class StudyCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criteria_id", nullable = false)
    private Long criteriaId;

    @Column(name = "description")
    private String description;
}

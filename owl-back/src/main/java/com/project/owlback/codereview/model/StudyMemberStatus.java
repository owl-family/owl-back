package com.project.owlback.codereview.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_member_status")
public class StudyMemberStatus {
    @Id
    @Column(name = "study_member_status_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false, length = 45)
    private String description;
}
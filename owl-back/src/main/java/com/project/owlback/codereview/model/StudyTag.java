package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_tag")
public class StudyTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_tag_id", nullable = false)
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;
}
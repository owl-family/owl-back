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
@Table(name = "study_tag")
public class StudyTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_tag_id", nullable = false)
    private Long studyTagId;

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "study_group_id")
    private Long studyGroupId;

    @Column(name = "count")
    private int count;
}

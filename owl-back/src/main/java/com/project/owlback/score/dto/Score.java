package com.project.owlback.score.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long scoreId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "SCORE_CATEGORY_ID")
    private long scoreCategoryId;

    @Column(name = "CREATED_DATE")
    private Date createDate;
}

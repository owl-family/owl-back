package com.project.owlback.score.dto;

import com.project.owlback.user.dto.User;
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

//    @Column(name = "USER_ID")
//    private long userId;

//    @Column(name = "SCORE_CATEGORY_ID")
//    private long scoreCategoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "SCORE_CATEGORY_ID")
    private ScoreCategory scoreCategory;
}

package com.project.owlback.score.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ScoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long scoreCategoryId;

    private String name;

    private int score;

    @OneToMany(mappedBy = "scoreCategory")
    private List<Score> scores = new ArrayList<>();
}

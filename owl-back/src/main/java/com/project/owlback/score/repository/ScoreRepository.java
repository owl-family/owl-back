package com.project.owlback.score.repository;

import com.project.owlback.score.dto.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}

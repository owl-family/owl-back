package com.project.owlback.score.service;

import com.project.owlback.score.dto.RankingDto;
import com.project.owlback.score.dto.ScoreDto;

import java.util.List;

public interface ScoreService {
    List<ScoreDto> getHistory(long userId, String service);

    RankingDto getRanking(long userId);
}

package com.project.owlback.score.service;

import com.project.owlback.score.dto.RankingDto;
import com.project.owlback.score.dto.ScoreDto;
import com.project.owlback.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Override
    public List<ScoreDto> getHistory(long userId, String service) {
        return scoreRepository.finaAllScoreByUserIdAndService(userId, service);
    }

    @Override
    public RankingDto getRanking(long userId) {
        return scoreRepository.findRankingByUserId(userId);
    }
}

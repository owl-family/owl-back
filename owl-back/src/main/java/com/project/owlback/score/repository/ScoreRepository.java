package com.project.owlback.score.repository;

import com.project.owlback.score.dto.RankingDto;
import com.project.owlback.score.dto.Score;
import com.project.owlback.score.dto.ScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(value = "SELECT * FROM SCORE s LEFT JOIN SCORE_CATEGORY sc ON s.score_category_id = sc.score_category_id where s.user_id = :userId and sc.name = :service", nativeQuery = true)
    List<ScoreDto> finaAllScoreByUserIdAndService(@PathVariable("userId") Long userId, @PathVariable("service") String service);

    @Query(value = "select ranking from (select user_id, rank() over (order by sum_value desc) as ranking from (select s.user_id, sum(sc.score) sum_value from score s join score_category sc on s.score_category_id = sc.score_category_id group by s.user_id) tb1) tb2 where user_id = :userId", nativeQuery = true)
    RankingDto findRankingByUserId(@PathVariable("userId") Long userId);
}

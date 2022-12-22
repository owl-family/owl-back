package com.project.owlback.score.controller;

import com.project.owlback.score.dto.RankingDto;
import com.project.owlback.score.dto.ScoreDto;
import com.project.owlback.score.service.ScoreService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;


    @GetMapping("{user_id}")
    public ResponseEntity<?> getHistory(@PathVariable("user_id") long userId, @RequestParam String service) {
        List<ScoreDto> scoreDtos = scoreService.getHistory(userId, service);
        return Response.makeResponse(HttpStatus.OK, "유저의 " + service + " 점수", scoreDtos.size(), scoreDtos);
    }

    @GetMapping("ranking/{user_id}")
    public ResponseEntity<?> getRanking(@PathVariable("user_id") long userId) {
        RankingDto rankingDto = scoreService.getRanking(userId);
        return Response.makeResponse(HttpStatus.OK, "유저 랭킹", 1, rankingDto);
    }
}

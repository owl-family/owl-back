package com.project.owlback.stat.controller;

import com.project.owlback.stat.dto.StatDto;
import com.project.owlback.stat.service.StatService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stats")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("{user_id}")
    public ResponseEntity<?> getStat(@PathVariable("user_id") long userId, @RequestParam String term) {
        List<StatDto> statDtos = statService.getStat(userId, term);
        return Response.makeResponse(HttpStatus.OK, "유저의 " + term + " 통계", statDtos.size(), statDtos);
    }
}

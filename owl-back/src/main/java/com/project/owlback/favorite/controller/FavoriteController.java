package com.project.owlback.favorite.controller;

import com.project.owlback.favorite.dto.FavoriteDto;
import com.project.owlback.favorite.dto.temp.CodeReviewDto;
import com.project.owlback.favorite.dto.temp.UrlDto;
import com.project.owlback.favorite.service.FavoriteService;
import com.project.owlback.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final Response response;

    @GetMapping("{user_id}")
    public ResponseEntity<?> getFavorites(@PathVariable("user_id") long userId, @RequestParam String service, @RequestParam(required = false) String search, @RequestParam(required = false) String query) {
        // search랑 query가 뭐임? 예시
        if (service.equals("code_review")) {
            List<CodeReviewDto> codeReviewDtos = favoriteService.getFavoritesByCodeReview(userId, search, query);
            return response.makeResponse(HttpStatus.OK, "유저의 " + service + " 즐겨찾기", codeReviewDtos.size(), codeReviewDtos);

        } else {
            List<UrlDto> urlDtos = favoriteService.getFavoritesByUrl(userId, search, query);
            return response.makeResponse(HttpStatus.OK, "유저의 " + service + " 즐겨찾기", urlDtos.size(), urlDtos);
        }
    }

    @PostMapping("{user_id}")
    public ResponseEntity<?> addFavorite(@PathVariable("user_id") long userId, @RequestBody FavoriteDto favoriteDto) {
        favoriteService.addFavorite(userId, favoriteDto);
        return response.makeResponse(HttpStatus.CREATED, "즐겨찾기 등록 완료");
    }

    @DeleteMapping("{user_id}/{favorite_id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable("user_id") long userId, @PathVariable("favorite_id") long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return response.makeResponse(HttpStatus.CREATED, "즐겨찾기 삭제 완료");
    }
}

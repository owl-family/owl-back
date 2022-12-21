package com.project.owlback.favorite.service;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.FavoriteDto;
import com.project.owlback.favorite.dto.temp.CodeReviewDto;
import com.project.owlback.favorite.dto.temp.UrlDto;

import java.util.List;

public interface FavoriteService {
    List<CodeReviewDto> getFavoritesByCodeReview(long userId, String search, String query);

    List<UrlDto> getFavoritesByUrl(long userId, String search, String query);

    Favorite addFavorite(long userId, FavoriteDto favoriteDto);

    void deleteFavorite(long favoriteId);
}

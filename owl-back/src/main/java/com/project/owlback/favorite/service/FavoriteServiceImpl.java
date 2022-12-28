package com.project.owlback.favorite.service;

import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.FavoriteDto;
import com.project.owlback.favorite.dto.temp.CodeReviewDto;
import com.project.owlback.favorite.dto.temp.UrlDto;
import com.project.owlback.favorite.dto.temp.UrlRepository;
import com.project.owlback.favorite.repository.FavoriteRepository;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CodeReviewRepository codeReviewRepository;
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @Override
    public List<CodeReviewDto> getFavoritesByCodeReview(long userId, String search, String query) {
        List<CodeReviewDto> codeReviewDtos;

        // 추후 search 조건에 따라 추가 필요
        if (search.equals("title")) {
            codeReviewDtos = favoriteRepository.findFavoritesByCodeReviewAndTitle(userId, query);
        } else {
            codeReviewDtos = favoriteRepository.findFavoritesByCodeReview(userId);
        }

        return codeReviewDtos;
    }

    @Override
    public List<UrlDto> getFavoritesByUrl(long userId, String search, String query) {
        List<UrlDto> urlDtos;

        // 추후 search 조건에 따라 추가 필요
        if (search.equals("title")) {
            urlDtos = favoriteRepository.findFavoritesByUrlAndTitle(userId, query);
        } else {
            urlDtos = favoriteRepository.findFavoritesByUrl(userId);
        }

        return urlDtos;
    }

    @Override
    public Favorite addFavorite(long userId, FavoriteDto favoriteDto) {
        Favorite newFavorite;

        if (favoriteDto.getService().equals("code_review")) {
            Favorite favorite = Favorite.builder()
                    .codeReview(codeReviewRepository.findById(favoriteDto.getServiceId()).get())
                    .user(userRepository.findById(userId).get())
                    .build();

            newFavorite = favoriteRepository.save(favorite);

        } else {
            Favorite favorite = Favorite.builder()
                    .url(urlRepository.findById(favoriteDto.getServiceId()).get())
                    .user(userRepository.findById(userId).get())
                    .build();

            newFavorite = favoriteRepository.save(favorite);
        }

        return newFavorite;
    }

    @Override
    public void deleteFavorite(long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}

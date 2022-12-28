package com.project.owlback.favorite.repository;

import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.temp.CodeReviewDto;
import com.project.owlback.favorite.dto.temp.UrlDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query(value = "select * from favorite f join code_review cr on f.code_review_id = cr.code_review_id where f.user_id = :userId", nativeQuery = true)
    List<CodeReviewDto> findFavoritesByCodeReview(@PathVariable("userId") Long userId);

    @Query(value = "select * from favorite f join url u on f.url_id = u.url_id where f.user_id = :userId", nativeQuery = true)
    List<UrlDto> findFavoritesByUrl(@PathVariable("userId") Long userId);

    @Query(value = "select * from favorite f join code_review cr on f.code_review_id = cr.code_review_id where f.user_id = :userId and cr.title like concat('%', :query, '%')", nativeQuery = true)
    List<CodeReviewDto> findFavoritesByCodeReviewAndTitle(@PathVariable("userId") long userId, @PathVariable("query") String query);

    @Query(value = "select * from favorite f join url u on f.url_id = u.url_id where f.user_id = :userId and u.title like concat('%', :query, '%')", nativeQuery = true)
    List<UrlDto> findFavoritesByUrlAndTitle(@PathVariable("userId") long userId, @PathVariable("query") String query);
}

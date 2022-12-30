package com.project.owlback.url.repository;

import com.project.owlback.url.model.UrlLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UrlLikeRepository extends JpaRepository<UrlLike, Long> {

    @Query(value="select * from url_like ul where ul.user_id = :userId and ul.url_review_id = :urlReviewId", nativeQuery = true )
    List<UrlLike> existsByUserIdAndReviewId(@PathVariable("userId") Long userId, @PathVariable("urlReviewId") Long urlReviewId);
}

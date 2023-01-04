package com.project.owlback.url.repository;

import com.project.owlback.url.model.UrlReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrlReviewRepository extends JpaRepository<UrlReview,Long> {
    @Query(value = "select * from url_review u where u.url_id = :urlId order by start_score desc",nativeQuery = true)
    Optional<List<UrlReview>> findByUrlId(@Param("urlId") Long urlId);
}

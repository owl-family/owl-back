package com.project.owlback.url.repository;

import com.project.owlback.url.model.UrlReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlReviewRepository extends JpaRepository<UrlReview,Long> {
}

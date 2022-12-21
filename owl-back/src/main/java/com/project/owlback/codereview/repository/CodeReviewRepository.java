package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {
}

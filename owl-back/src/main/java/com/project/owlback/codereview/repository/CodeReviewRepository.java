package com.project.owlback.codereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.owlback.codereview.model.CodeReview;
@Repository
public interface CodeReviewRepository extends JpaRepository<CodeReview, Integer> {

}

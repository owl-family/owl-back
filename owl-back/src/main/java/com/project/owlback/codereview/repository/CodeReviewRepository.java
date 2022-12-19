package com.project.owlback.codereview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.CodeReview;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Integer> {
	CodeReview findById(int id);
}

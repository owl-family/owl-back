package com.project.owlback.codereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;

public interface CodeRevieHistoryRepository extends JpaRepository<CodeHistory, Integer> {
	List<CodeHistory> findByCodeReview(CodeReview codeReview);
}

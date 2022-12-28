package com.project.owlback.codereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import java.sql.SQLException;
import java.util.Optional;

public interface CodeReviewHistoryRepository extends JpaRepository<CodeHistory, Long> {
	List<CodeHistory> findByCodeReview(CodeReview codeReview);

    Optional<CodeHistory> findByCodeReviewIdAndVersionNum(int codeReviewId, int versionNum) throws SQLException;
}

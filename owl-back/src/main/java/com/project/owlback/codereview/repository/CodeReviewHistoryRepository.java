package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeReviewHistoryRepository extends JpaRepository<CodeHistory, Integer> {
    CodeHistory findByCodeReviewIdAndVersionNum(int codeReviewId, int versionNum);
}

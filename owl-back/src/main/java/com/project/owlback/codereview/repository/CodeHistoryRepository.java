package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeHistoryRepository extends JpaRepository<CodeHistory, Long> {
}

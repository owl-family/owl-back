package com.project.owlback.codereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeHistoryTag;

public interface CodeHistoryTagRepository extends JpaRepository<CodeHistoryTag, Long>{
	List<CodeHistoryTag> findByCodeHistory(CodeHistory codeHistory);
}

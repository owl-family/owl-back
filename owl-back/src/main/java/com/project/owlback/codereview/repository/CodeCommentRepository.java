package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CodeCommentRepository extends JpaRepository<CodeComment, Integer> {
    List<CodeComment> findByCodeHistoryId(int id);

}
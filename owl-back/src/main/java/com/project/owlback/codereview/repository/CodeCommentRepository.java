package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeCommentRepository extends JpaRepository<CodeComment, Integer> {
}

package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CodeCommentLikeRepository extends JpaRepository<CodeCommentLike, Integer> {
    int countByUserIdAndCodeCommentId(int userId, int codeCommentId);
}

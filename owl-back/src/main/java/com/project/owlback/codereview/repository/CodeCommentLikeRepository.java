package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface CodeCommentLikeRepository extends JpaRepository<CodeCommentLike, Long> {
    int countByUserIdAndCodeCommentId(long userId, long codeCommentId) throws SQLException;
}

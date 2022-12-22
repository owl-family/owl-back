package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeCommentLike;
import com.project.owlback.codereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface CodeCommentLikeRepository extends JpaRepository<CodeCommentLike, Long> {
    int countByUserIdAndCodeCommentId(long userId, long codeCommentId) throws SQLException;
    Optional<CodeCommentLike> findByCodeCommentAndUser(CodeComment comment, User user);
}

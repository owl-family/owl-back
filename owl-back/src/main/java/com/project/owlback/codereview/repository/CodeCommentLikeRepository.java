package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeCommentLike;
import com.project.owlback.codereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeCommentLikeRepository extends JpaRepository<CodeCommentLike, Long> {
    Optional<CodeCommentLike> findByCodeCommentAndUser(CodeComment comment, User user);
}

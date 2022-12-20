package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.List;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {

    Page<CodeReview> findAll(Pageable pageable);

    Page<CodeReview> findByStudyGroupId(int id, Pageable pageable) throws SQLException;

    Page<CodeReview> findByTitleLike(String word, Pageable pageable) throws SQLException;

    Page<CodeReview> findByWriterNicknameLike(String word, Pageable pageable) throws SQLException;

    Page<CodeReview> findByCodeLanguageDescriptionLike(String word, Pageable pageable) throws SQLException;
}

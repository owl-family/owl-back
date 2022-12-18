package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Integer> {

    List<CodeReview> findByStudyGroupId(int id) throws SQLException;

    List<CodeReview> findByTitleLike(String word) throws SQLException;

    List<CodeReview> findByWriterNicknameLike(String word) throws SQLException;

    List<CodeReview> findByCodeLanguageDescriptionLike(String word) throws SQLException;
}

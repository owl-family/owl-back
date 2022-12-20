package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeReviewTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

public interface CodeReviewTagRepository extends JpaRepository<CodeReviewTag, Long> {
    List<CodeReviewTag> findByCodeReviewId(int id) throws SQLException;

    Page<CodeReviewTag> findByTagContentLike(String content, Pageable pageable) throws SQLException;

    List<CodeReviewTag> findByTagContentLikeOrderByCountDesc(String content) throws SQLException;

}

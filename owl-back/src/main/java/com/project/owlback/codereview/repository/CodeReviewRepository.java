package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Integer> {


    List<CodeReview> findAll();

    List<CodeReview> findByStudyGroupId(int id);

    List<CodeReview> findByTitleLike(String word);

    List<CodeReview> findByWriterNicknameLike(String word);

    List<CodeReview> findByCodeLanguageDescriptionLike(String word);
}

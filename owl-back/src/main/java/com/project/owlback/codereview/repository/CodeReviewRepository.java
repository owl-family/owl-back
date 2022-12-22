package com.project.owlback.codereview.repository;


import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.owlback.codereview.model.CodeReview;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {
//	Optional<CodeReview> findById(Long id);
	
	@Modifying
	@Query("update CodeReview set viewCount = viewCount+1 where id = :id")
	void CountUp(@Param("id") Long content);
}

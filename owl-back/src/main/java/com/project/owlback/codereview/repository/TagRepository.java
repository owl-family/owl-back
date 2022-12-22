package com.project.owlback.codereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.owlback.codereview.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	boolean existsByContent(String content);
	
	Tag findByContent(String content);
	
	@Modifying
	@Query("update Tag set count = count+1 where content = :content")
	void CountUp(@Param("content") String content);
}

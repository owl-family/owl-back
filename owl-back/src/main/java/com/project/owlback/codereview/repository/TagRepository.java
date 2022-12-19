package com.project.owlback.codereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	boolean existsByContent(String content);
	
	Tag findByContent(String content);
}

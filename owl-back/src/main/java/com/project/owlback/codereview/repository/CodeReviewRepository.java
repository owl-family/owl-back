package com.project.owlback.codereview.repository;


import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;

@Transactional
public class CodeReviewRepository {
	@Autowired(required = true)
	CodeReviewRepo codereviewRepo;
	
}

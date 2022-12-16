package com.project.owlback.codereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.owlback.codereview.model.CodeHistory;

public interface CodeRevieHistoryRepository extends JpaRepository<CodeHistory, Integer> {

}

package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByContentLikeOrderByCountDesc(String word) throws SQLException;
}

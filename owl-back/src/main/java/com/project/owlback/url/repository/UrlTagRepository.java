package com.project.owlback.url.repository;

import com.project.owlback.codereview.model.Tag;
import com.project.owlback.url.model.UrlTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlTagRepository extends JpaRepository<UrlTag, Long> {
}

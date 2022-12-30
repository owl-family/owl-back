package com.project.owlback.url.repository;

import com.project.owlback.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}

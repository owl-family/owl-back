package com.project.owlback.url.repository;

import com.project.owlback.codereview.model.Tag;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.model.UrlTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlTagRepository extends JpaRepository<UrlTag, Long> {
    List<UrlTag> findByTagId(Long id);

    List<UrlTag> findByUrl(Url build);
}

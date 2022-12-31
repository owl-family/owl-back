package com.project.owlback.url.repository;

import com.project.owlback.url.dto.UrlGetDto;
import com.project.owlback.url.model.Url;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query(value = "select u from Url u where u.createdDate BETWEEN DATE_ADD(NOW(), INTERVAL -1 :itv) AND NOW()",nativeQuery = true)
    List<Url> findByAllTime(@Param("itv") String itv);
}

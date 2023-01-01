package com.project.owlback.url.repository;

import com.project.owlback.url.dto.UrlGetDto;
import com.project.owlback.url.model.Url;
import com.project.owlback.url.model.UrlTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query(value = "select * from Url u where u.created_date >= DATE_ADD(NOW(), INTERVAL - :itv day ) order by created_date desc",nativeQuery = true)
    List<Url> findByAllTime(@Param("itv") Integer itv);

    List<Url> findByTitleContaining(String word);

}

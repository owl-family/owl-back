package com.project.owlback.stat.repository;

import com.project.owlback.stat.dto.StatSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatSubjectRepository extends JpaRepository<StatSubject, Long> {
    List<StatSubject> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}

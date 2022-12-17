package com.project.owlback.stat.repository;

import com.project.owlback.stat.dto.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {
}

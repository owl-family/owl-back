package com.project.owlback.stat.repository;

import com.project.owlback.stat.dto.Stat;
import com.project.owlback.stat.dto.StatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query(value = "select * from stat_subject ss join subject s on ss.subject_id = s.subject_id where s.user_id = :userId and start_time >= date_add(now(), interval -1 day)", nativeQuery = true)
    List<StatDto> finaAllStatsByUserIdAndDay(@PathVariable("userId") Long userId);

    @Query(value = "select * from stat_subject ss join subject s on ss.subject_id = s.subject_id where s.user_id = :userId and start_time >= date_add(now(), interval -1 week)", nativeQuery = true)
    List<StatDto> finaAllStatsByUserIdAndWeek(@PathVariable("userId") Long userId);

    @Query(value = "select * from stat_subject ss join subject s on ss.subject_id = s.subject_id where s.user_id = :userId and start_time >= date_add(now(), interval -1 month)", nativeQuery = true)
    List<StatDto> finaAllStatsByUserIdAndMonth(@PathVariable("userId") Long userId);

}
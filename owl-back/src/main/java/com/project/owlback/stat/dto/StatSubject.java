package com.project.owlback.stat.dto;

import com.project.owlback.goal.dto.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StatSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statSubjectId;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "STAT_ID")
    private Stat stat;
}

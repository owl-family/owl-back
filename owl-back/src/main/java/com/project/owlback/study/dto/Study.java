package com.project.owlback.study.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyId;

    @Column(name = "USER_ID")
    private long userId;

}

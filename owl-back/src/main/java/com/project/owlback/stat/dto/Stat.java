package com.project.owlback.stat.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statId;

    @Column(name = "USER_ID")
    private long userId;

}

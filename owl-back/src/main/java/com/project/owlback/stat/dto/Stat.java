package com.project.owlback.stat.dto;

import com.project.owlback.user.dto.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "stat")
    private List<StatSubject> statSubjects = new ArrayList<>();
}

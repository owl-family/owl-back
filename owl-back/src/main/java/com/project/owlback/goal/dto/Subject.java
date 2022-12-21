package com.project.owlback.goal.dto;

import com.project.owlback.stat.dto.StatSubject;
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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subjectId;

    private String name;

    private int active;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "subject")
    private List<StatSubject> statSubjects = new ArrayList<>();
}

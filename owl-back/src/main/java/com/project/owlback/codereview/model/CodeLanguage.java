package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code_language")
@Getter
@Setter
public class CodeLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_language_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;

}
package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_language")
@Getter
@Setter
@Builder
public class CodeLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_language_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;

}
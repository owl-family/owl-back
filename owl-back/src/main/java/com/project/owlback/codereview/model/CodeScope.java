package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "code_scope")
public class CodeScope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_scope_id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false, length = 20)
    private String description;

}
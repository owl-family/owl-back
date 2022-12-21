package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "code_history_tag")
public class CodeHistoryTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_history_tag_id", nullable = false)
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_history_id", nullable = false)
    private CodeHistory codeHistory;
}
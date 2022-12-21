package com.project.owlback.codereview.model;

import java.util.List; 

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor@Entity
@Getter
@Setter
@Builder
@ToString(of = {"id","count","tag","codeHistory"})
@Table(name = "code_history_tag")
public class CodeHistoryTag {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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
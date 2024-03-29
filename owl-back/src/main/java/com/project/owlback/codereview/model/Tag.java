package com.project.owlback.codereview.model;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@Table(name = "tag")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id","content","count"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, length = 30)
    private String content;

    @Column(name = "count", nullable = false)
    private Integer count;
}
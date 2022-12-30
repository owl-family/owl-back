package com.project.owlback.url.model;


import com.project.owlback.codereview.model.Tag;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "url_tag")
public class UrlTag {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "url_tag_id", nullable = false)
    private Long urlTagId;

   @ManyToOne(optional = false)
   @JoinColumn(name="url_id", nullable = false)
    private Long urlId;

    @ManyToOne(optional = false)
    @JoinColumn(name="tag_id", nullable = false)
    private Tag tag;


}

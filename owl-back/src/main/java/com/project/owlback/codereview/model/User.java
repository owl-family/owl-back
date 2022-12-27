package com.project.owlback.codereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "user")
public class User {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @ToString.Include
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ToString.Include
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @ToString.Include
    @Column(name = "img_file")
    private byte[] imgFile;

    @ToString.Include
    @Column(name = "introduction", length = 1000)
    private String introduction;

    @ToString.Include
    @Column(name = "password", length = 1000)
    private String password;

    @ToString.Include
    @Column(name = "created_date")
    private Instant createdDate;

    @ToString.Include
    @Column(name = "modified_date")
    private Instant modifiedDate;

    @ToString.Include
    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "writer")
    private List<CodeComment> comments = new ArrayList<>();
}
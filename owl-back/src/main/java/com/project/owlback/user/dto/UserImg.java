package com.project.owlback.user.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name="user_img")
public class UserImg {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="img_id")
    private Long imgId;

    @Column(name="file_name", nullable = false)
    private String fileName;
    @Column(name="file_original_name", nullable = false)
    private String fileOriginalName;

    @Column(name="file_url", nullable = false)
    private String fileUrl;
}

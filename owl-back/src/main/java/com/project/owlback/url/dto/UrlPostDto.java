package com.project.owlback.url.dto;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlPostDto {
    private Long userId;
    private String title;
    private String content;
    private String link;


}

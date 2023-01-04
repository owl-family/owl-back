package com.project.owlback.url.dto;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlReviewDto {
    private Long userId;
    private Long startScore;
    private String content;

}

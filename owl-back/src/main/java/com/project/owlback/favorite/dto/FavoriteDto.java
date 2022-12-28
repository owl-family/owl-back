package com.project.owlback.favorite.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteDto {
    private String service;
    private Long serviceId;
}

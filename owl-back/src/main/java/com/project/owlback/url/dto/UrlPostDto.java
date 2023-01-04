package com.project.owlback.url.dto;


import com.project.owlback.codereview.model.Tag;
import lombok.*;

import java.util.List;

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
    private List<Tag> tag;


}

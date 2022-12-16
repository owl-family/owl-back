package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewDto {
    int id;
    List<String> tags;
    String title;
    int versionCount;
    String nickname;
    String language;
    int viewCount;
    int commentCount;
    Instant createDate;
}

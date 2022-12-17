package com.project.owlback.codereview.dto;

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
public class CodeHistoryDetailDto {
    int id;
    String title;
    String subTitle;
    int version;
    Instant createdDate;
    int like;
    String code;
    String contents;
    List<CodeCommentDetailDto> comments;

}

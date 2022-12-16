package com.project.owlback.codereview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeCommentDto {
    String nickname;
    String contents;
    int startLine;
    int endLine;
    int parent;
    int depth;
    int likeCount;
    Instant createdDate;
    boolean like;
}

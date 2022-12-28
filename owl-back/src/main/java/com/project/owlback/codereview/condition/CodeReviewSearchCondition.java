package com.project.owlback.codereview.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewSearchCondition {
    long codeReviewId;
    long codeHistoryId;
    long studyGroupId;
    int startLine;
    String key;
    String title;
    String writer;
    String language;
    String tag;

}

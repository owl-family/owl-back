package com.project.owlback.codereview.dto;

import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.studygroup.model.StudyGroup;

import com.project.owlback.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"title","writer","studyGroup","codeScope","codeLanguage","codeHistoryPostDto"})
public class CodeReviewPostDto {

    private String title;

    private User writer;

    private StudyGroup studyGroup;

    private CodeScope codeScope;

    private CodeLanguage codeLanguage;
    
    private CodeHistoryPostDto codeHistoryPostDto;

}

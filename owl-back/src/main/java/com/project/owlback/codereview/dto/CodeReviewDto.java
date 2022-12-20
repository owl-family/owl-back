package com.project.owlback.codereview.dto;



import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeLanguage;
import com.project.owlback.codereview.model.CodeScope;
import com.project.owlback.codereview.model.StudyGroup;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.model.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString(of = {"id","versionCount","title","createdDate","modifiedDate","viewCount","commentCount","writer","studyGroup","codeScope","codeLanguage","codeHistory","tag"})
public class CodeReviewDto {

    private Long id;

    private Integer versionCount;

    private String title;

    private Instant createdDate;

    private Instant modifiedDate;

    private Integer viewCount;

    private Integer commentCount;

    private User writer;

    private StudyGroup studyGroup;

    private CodeScope codeScope;

    private CodeLanguage codeLanguage;
    
    private CodeHistory codeHistory;
    
    private List<Tag> tag;
}

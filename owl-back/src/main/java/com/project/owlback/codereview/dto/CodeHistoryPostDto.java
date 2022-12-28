package com.project.owlback.codereview.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id","versionNum","code","subTitle","contents","codeReview"})
public class CodeHistoryPostDto{
    private Integer versionNum;

    private String code;

    private String subTitle;

    private String contents;

    private CodeReview codeReview;
    
    private List<Tag> tag;

}
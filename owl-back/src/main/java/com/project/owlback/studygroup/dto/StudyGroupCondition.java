package com.project.owlback.studygroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroupCondition {
    private long studyId;
    private long userId;
    private String description;
}

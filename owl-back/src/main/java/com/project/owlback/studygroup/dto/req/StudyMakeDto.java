package com.project.owlback.studygroup.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudyMakeDto {
    private String name;
    private String goal;
    private String study_information;
    private Long join_process_id;
    private Long study_criteria_id;
    private String criteria_detail;
    private int max_member;
    private Long user_id;
}

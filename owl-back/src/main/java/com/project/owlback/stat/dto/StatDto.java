package com.project.owlback.stat.dto;

import java.util.Date;

public interface StatDto {
    Date getStart_Time();
    Date getEnd_Time();
    int getActive();
    String getName();
}

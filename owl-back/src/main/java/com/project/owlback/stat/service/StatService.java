package com.project.owlback.stat.service;

import com.project.owlback.stat.dto.StatDto;

import java.util.List;

public interface StatService {
    List<StatDto> getStat(long userId, String term);
}

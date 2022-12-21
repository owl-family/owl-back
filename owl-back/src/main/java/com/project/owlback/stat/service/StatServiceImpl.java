package com.project.owlback.stat.service;

import com.project.owlback.stat.dto.StatDto;
import com.project.owlback.stat.repository.StatRepository;
import com.project.owlback.stat.repository.StatSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatSubjectRepository statSubjectRepository;

    @Override
    public List<StatDto> getStat(long userId, String term) {
        List<StatDto> statDtos = null;

        switch (term) {
            case "today":
                statDtos = statRepository.finaAllStatsByUserIdAndDay(userId);
                break;
            case "week":
                statDtos = statRepository.finaAllStatsByUserIdAndWeek(userId);
                break;
            case "month":
                statDtos = statRepository.finaAllStatsByUserIdAndMonth(userId);
                break;
            default:
        }

        return statDtos;
    }
}

package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.repository.MinuteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MinuteService {
    private final MinuteRepository minuteRepository;

    public Minute save(Minute minute) {
        return minuteRepository.save(minute);
    }

    public Minute findTodayMinute(int projectId) {
        return minuteRepository.findByProjectIdAndDate(projectId, LocalDate.now());
    }

    public Minute findMinuteWhen(int projectId, LocalDate when) {
        return minuteRepository.findByProjectIdAndDate(projectId, when);
    }

    public List<Minute> findMinutesByProjectId(Integer projectId, int page, int size) {
        return minuteRepository.findByProjectId(projectId, PageRequest.of(page, size));
    }
}

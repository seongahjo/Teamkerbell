package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.repository.MinuteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
}

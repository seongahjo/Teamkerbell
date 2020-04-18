package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimelineService {
    private final TimelineRepository timelineRepository;

    public Timeline append(TimelineAdapter timeline) {
        return timelineRepository.save(Timeline.from(timeline));
    }

    public List<Timeline> getTimelinesByUserId(Integer userId, int page, int size) {
        return timelineRepository.findByUserId(userId, PageRequest.of(page, size));
    }
}

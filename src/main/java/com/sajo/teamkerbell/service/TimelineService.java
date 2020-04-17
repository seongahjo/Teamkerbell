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

    public Timeline append(Schedule schedule) {
        return timelineRepository.save(Timeline.from(schedule));
    }

    public Timeline append(FileDB fileDB) {
        return timelineRepository.save(Timeline.from(fileDB));
    }

    public Timeline append(TodoList todoList) {
        return timelineRepository.save(Timeline.from(todoList));
    }

    public Timeline append(Minute minute) {
        return timelineRepository.save(Timeline.from(minute));
    }

    public List<Timeline> getTimelinesByUserId(Integer userId, int page, int size) {
        return timelineRepository.findByUserId(userId, PageRequest.of(page, size));
    }
}

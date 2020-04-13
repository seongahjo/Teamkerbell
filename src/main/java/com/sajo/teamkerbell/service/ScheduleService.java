package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface ScheduleService {
    Schedule getSchedule(Integer scheduleIdx);

    List<Schedule> getSchedules(User u);

    Schedule save(Schedule s);

    void clear(User u);
}

package com.shape.web.service;

import com.shape.web.entity.Project;
import com.shape.web.entity.Schedule;
import com.shape.web.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface ScheduleService {
    Schedule getSchedule(Integer scheduleIdx);
    List getSchedules(User u);
    List getSchedules(Project p);
    Schedule save(Schedule s);
    void clear(User u);
}

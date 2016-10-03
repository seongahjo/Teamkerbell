package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.Schedule;
import com.shape.web.entity.User;
import com.shape.web.repository.ScheduleRepository;
import com.shape.web.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    /*
    schedule:'scheduleidx'
    user:'useridx':schedules
    project:'projectidx':schedules
     */

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    @Cacheable(value = "schedule", key = "'schedule:'.concat(#p0)")
    public Schedule getSchedule(Integer scheduleIdx) {
        return scheduleRepository.findOne(scheduleIdx);
    }

    @Override
    @Cacheable(value = "schedules", key = "'user:'.concat(#p0.useridx).concat(':schedules')")
    public List getSchedules(User u) {
        return scheduleRepository.findByProject_Users(u);
    }

    @Override
    @Cacheable(value = "schedules", key = "'project:'.concat(#p0.projectidx).concat(':schedules')")
    public List getSchedules(Project p) {
        return null;
    }


    //Evict


    @Override
    @Caching(evict = {
            @CacheEvict(value = "schedules", key = "'project:'.concat(#p0.project.projectidx).concat(':schedules')"),
            @CacheEvict(value = "schedule", key = "'schedule:'.concat(#p0.scheduleidx)"),
    })
    public Schedule save(Schedule s) {
        return scheduleRepository.saveAndFlush(s);
    }

    @Override
    @CacheEvict(value = "schedules", key = "'user:'.concat(#p0.useridx).concat(':schedules')")
    public void clear(User u) {}
}

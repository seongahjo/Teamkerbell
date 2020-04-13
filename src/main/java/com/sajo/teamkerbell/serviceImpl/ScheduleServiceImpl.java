package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.repository.ScheduleRepository;
import com.sajo.teamkerbell.service.ScheduleService;
import com.sajo.teamkerbell.entity.User;
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

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Cacheable(value = "schedule", key = "'schedule:'.concat(#p0)")
    public Schedule getSchedule(Integer scheduleIdx) {
        return scheduleRepository.findOne(scheduleIdx);
    }

    @Override
    public List<Schedule> getSchedules(User u) {
        return scheduleRepository.findByProject_Users(u);
    }


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
    public void clear(User u) {
        /*
        * Add Action If you want
        * */
    }
}

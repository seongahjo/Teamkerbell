package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.Alarm;
import com.sajo.teamkerbell.repository.AlarmRepository;
import com.sajo.teamkerbell.service.AlarmService;
import com.sajo.teamkerbell.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by seongahjo on 2016. 7. 18..
 */
@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {
    private AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public Alarm create(Alarm alarm) {
        save(alarm);
        return alarm;
    }

    @Override
    @Cacheable(value = "alarm", key = "'alarm:'.concat(#p0)")
    public Alarm getAlarm(Integer idx) {
        return alarmRepository.findOne(idx);
    }

    @Override
    public List getAlarms(User u) {
        return alarmRepository.findByUserAndContentidAndIsshowOrderByDateDesc(u, 0, true);
    }


    @Override
    public Alarm getAlarm(User u) {
        return alarmRepository.findFirstByContentidAndUserOrderByDateDesc(0, u);
    }


    @Override
    public List<Alarm> getTimelines(User u, Integer page, Integer count) {
        return alarmRepository.findByUserOrderByDateDesc(u, new PageRequest(page, count));
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "alarms", key = "'user:'.concat(#p0.user.useridx).concat(':alarms')"),
            @CacheEvict(value = "alarm", key = "'alarm:'.concat(#p0.alarmidx)"),
            @CacheEvict(value = "alarm", key = "'user:'.concat(#p0.user.useridx).concat(':alarm')"),
            @CacheEvict(value = "alarms", key = "'user:'.concat(#p0.user.useridx).concat(':timelines')")
    })
    public Alarm save(Alarm a) {
        return alarmRepository.saveAndFlush(a);
    }
}
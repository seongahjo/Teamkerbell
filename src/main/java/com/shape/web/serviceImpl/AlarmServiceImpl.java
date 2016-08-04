package com.shape.web.serviceImpl;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.service.AlarmService;
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
@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmRepository alarmRepository;

    @Override
    //@CacheEvict(value="alarms",key="'users:'.concat(#p0.user.id).concat(':alarms')")
    public Alarm create(Alarm alarm) {
        Alarm temp = alarmRepository.findFirstByUserAndActorAndContentidAndIsshowOrderByDateDesc(alarm.getUser(), alarm.getActor(), 0, true);
        if (temp != null) {
            return null;
        }
        alarmRepository.saveAndFlush(alarm);

        return alarm;
    }

    @Override
    @Cacheable(value = "alarm", key = "'alarm:'.concat(#p0)")
    public Alarm getAlarm(Integer idx) {
        return alarmRepository.findOne(idx);
    }

    @Override
    @Cacheable(value = "alarms", key = "'user:'.concat(#p0.id).concat(':alarms')")
    public List getAlarms(User u) {
        return alarmRepository.findByUserAndContentidAndIsshowOrderByDateDesc(u, 0, true);
    }


    @Override
    @Cacheable(value = "alarm", key = "'user:'.concat(#p0.id).concat(':alarm')")
    public Alarm getAlarm(User u) {
        return alarmRepository.findFirstByContentidAndUserOrderByDateDesc(0, u);
    }


    @Override
    @Cacheable(value = "alarms", key = "'user:'.concat(#p0.id).concat(':timelines')")
    public List<Alarm> getTimelines(User u, Integer page, Integer count) {
        return alarmRepository.findByUserOrderByDateDesc(u, new PageRequest(page, count));
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "alarms", key = "'user:'.concat(#p0.user.id).concat(':alarms')"),
            @CacheEvict(value = "alarm", key = "'alarm:'.concat(#p0.alarmidx)"),
            @CacheEvict(value = "alarm", key = "'user:'.concat(#p0.user.id).concat(':alarm')"),
            @CacheEvict(value = "alarms", key = "'user:'.concat(#p0.user.id).concat(':timelines')"),
    })
    public Alarm save(Alarm a) {
        return alarmRepository.saveAndFlush(a);
    }
}
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
    /*
    alarm:'alamridx'
    user:'useridx':alarms
    user:'useridx':alarm
    user:'useridx':timelines
     */
    @Autowired
    AlarmRepository alarmRepository;

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
    @Cacheable(value = "alarms", key = "'user:'.concat(#p0.useridx).concat(':alarms')")
    public List getAlarms(User u) {
        return alarmRepository.findByUserAndContentidAndIsshowOrderByDateDesc(u, 0, true);
    }


    @Override
    @Cacheable(value = "alarm", key = "'user:'.concat(#p0.useridx).concat(':alarm')")
    public Alarm getAlarm(User u) {
        return alarmRepository.findFirstByContentidAndUserOrderByDateDesc(0, u);
    }


    @Override
    @Cacheable(value = "alarms", key = "'user:'.concat(#p0.useridx).concat(':timelines')")
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
        Alarm temp = alarmRepository.findFirstByUserAndActorAndContentidAndIsshowOrderByDateDesc(a.getUser(), a.getActor(), 0, true);
        if (temp != null) {
            return null;
        }
        return alarmRepository.saveAndFlush(a);
    }
}
package com.shape.web.serviceImpl;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "alarms", key = "'user:'.concat(#p0.id).concat(':alarms')")
    public List getAlarms(User u) {
        return alarmRepository.findByUserAndContentidAndIsshowOrderByDateDesc(u, 0, true);
    }

    @Override
    public Alarm save(Alarm a) {
        return null;
    }
}
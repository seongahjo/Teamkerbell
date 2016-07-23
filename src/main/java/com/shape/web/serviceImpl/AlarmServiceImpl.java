package com.shape.web.serviceImpl;

import com.shape.web.entity.Alarm;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 18..
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    @Autowired
    AlarmRepository alarmRepository;

    @Override
    public Alarm create(Alarm alarm) {
        Alarm temp = alarmRepository.findFirstByUserAndActorAndContentidAndIsshowOrderByDateDesc(alarm.getUser(), alarm.getActor(), 0, true);
        if (temp != null) {
            return null;
        }
        alarmRepository.saveAndFlush(alarm);
        return alarm;
    }
}
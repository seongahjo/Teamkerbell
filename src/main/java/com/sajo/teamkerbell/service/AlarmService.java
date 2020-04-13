package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Alarm;
import com.sajo.teamkerbell.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 18..
 */
@Service
public interface AlarmService {
    Alarm create(Alarm alarm);

    Alarm getAlarm(Integer idx);

    List<Alarm> getAlarms(User u);

    Alarm getAlarm(User u); // 알람 하나

    List<Alarm> getTimelines(User u, Integer page, Integer count); //타임라인

    Alarm save(Alarm a);
}

package com.shape.web.service;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 18..
 */
@Service
public interface AlarmService {
    Alarm create(Alarm alarm);
    List getAlarms(User u);
    Alarm save(Alarm a);
}

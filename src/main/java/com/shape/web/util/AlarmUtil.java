package com.shape.web.util;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by seongahjo on 2016. 9. 15..
 */

public class AlarmUtil {
    private static final Logger logger = LoggerFactory.getLogger(AlarmUtil.class);

    @Autowired
    static ScheduleService scheduleService;
    @Autowired
    static AlarmService alarmService;

    public static void postAlarm(final List<User>lu,Alarm alarm,final boolean isSchedule){
        makeAlarm(lu,alarm,isSchedule);
    }
    private static void makeAlarm(final List<User> lu, Alarm alarm,final boolean isSchedule){
        lu.forEach(u -> {
            alarm.setAlarmidx(null);
            logger.info("[USER " + u.getUseridx() + "] Make Alarm");
            alarm.setUser(u);
            if(isSchedule)
                scheduleService.clear(u);
            alarmService.save(alarm);
        });
    }
}

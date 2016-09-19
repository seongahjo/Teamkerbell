package com.shape.web.util;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by seongahjo on 2016. 9. 15..
 */
@Component
public class AlarmUtil {
    private static final Logger logger = LoggerFactory.getLogger(AlarmUtil.class);




    static ScheduleService scheduleService;
    static AlarmService alarmService;

    @Autowired(required = true)
    public void setScheduleService(ScheduleService scheduleService) {
        AlarmUtil.scheduleService = scheduleService;
    }

    @Autowired(required = true)
    public void setAlarmService(AlarmService alarmService) {
        AlarmUtil.alarmService = alarmService;
    }
    public static void postAlarm(final List<User>lu,Alarm alarm,final boolean isSchedule){
        makeAlarm(lu,alarm,isSchedule);
    }
    private static void makeAlarm(final List<User> lu, Alarm alarm,final boolean isSchedule){
        logger.info("MAKE");
        lu.forEach(u -> {
            alarm.setAlarmidx(null);
            alarm.setUser(u);
            logger.info("[USER " + u.getUseridx() + "] Make Alarm");
            if(isSchedule)
                scheduleService.clear(u);
            alarmService.save(alarm);
            logger.info("SAVE");
        });
    }
}

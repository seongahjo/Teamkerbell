package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hootting on 2016. 9. 13..
 */
@RestController
public class AlarmController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);


    @Autowired
    UserService userService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    ProjectService projectService;

    /*
    To accept invite request
     */
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public void acceptRequest(@RequestParam("alarmIdx") Integer alarmIdx, @RequestParam("type") Integer type) {
        Alarm alarm = alarmService.getAlarm(alarmIdx);
        alarm.setIsshow(false);
        if (type == 1)
            alarm.getUser().addProject(alarm.getProject());
        // userRepository.saveAndFlush(alarm.getUser());
        alarmService.save(alarm);
        projectService.save(alarm.getUser(),alarm.getProject());
    }

    /*
    To get invite request
     */
    @RequestMapping(value = "/updateAlarm", method = RequestMethod.GET)
    public Map updateAlarm(@RequestParam("userId") String userId) {
        Alarm alarm = alarmService.getAlarm(userService.getUserById(userId));
        Map<String, String> data = new HashMap<>();
        if (alarm != null) {
            data.put("alarmidx", String.valueOf(alarm.getAlarmidx()));
            data.put("projectname", alarm.getProject().getName());
            data.put("actorid", alarm.getActor().getId());
        }
        return data;
    }

    @RequestMapping(value = "/moreTimeline", method = RequestMethod.GET)
    public List moreSchedule(@RequestParam("page") Integer page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List timeline = alarmService.getTimelines(user, page+1,20);
        logger.info("REQUEST more timeline");
        if(timeline.size()==0)
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST,"NO MORE TIMELINE");
        return timeline;
    }
}

package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hootting on 2016. 9. 13..
 */
@Log
@RestController
public class AlarmController {

 
    @Autowired
    UserService userService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    ProjectService projectService;


    @RequestMapping(value="/timeline/{userId}",method= RequestMethod.GET)
    public List timeline(@PathVariable("userId") String userId,
                         @RequestParam(value="page",defaultValue = "0")Integer page,
                         @RequestParam(value="count",defaultValue = "20") Integer count){
        return alarmService.getTimelines(userService.getUserById(userId),page,count);
    }

    @RequestMapping(value="/alarm/{userId}",method= RequestMethod.GET)
    public List alarms(@PathVariable("userId") String userId){
        return alarmService.getAlarms(userService.getUserById(userId));
    }


    /*
    To accept invite request
     */
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public void acceptRequest(@RequestParam("alarmIdx") Integer alarmIdx, @RequestParam("type") Integer type) {
        Alarm alarm = alarmService.getAlarm(alarmIdx);
        alarm.setIsshow(false);
        if (type == 1)
            alarm.getUser().addProject(alarm.getProject());
        alarmService.save(alarm);
        projectService.save(alarm.getUser(),alarm.getProject());
    }

    /*
    To get invite request
     */
    // socket으로 받아오는거
    @RequestMapping(value = "/updateAlarm", method = RequestMethod.GET)
    public Alarm updateAlarm(@RequestParam("userId") String userId) {
        Alarm alarm = alarmService.getAlarm(userService.getUserById(userId));
        return alarm;
        /*
        Map<String, String> data = new HashMap<>();
        if (alarm != null) { // 이걸 Rest API로 대체할까말까 고민.
            data.put("alarmidx", String.valueOf(alarm.getAlarmidx()));
            data.put("projectname", alarm.getProject().getName());
            data.put("actorid", alarm.getActor().getId());
        }
        return data;
        */
    }

    @RequestMapping(value = "/moreTimeline", method = RequestMethod.GET)
    public List moreSchedule(@RequestParam("page") Integer page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List timeline = alarmService.getTimelines(user, page+1,20);
        log.info("REQUEST more timeline");
        if(timeline.size()==0)
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST,"NO MORE TIMELINE");
        return timeline;
    }

}

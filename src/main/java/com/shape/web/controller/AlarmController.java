package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hootting on 2016. 9. 13..
 */
@Slf4j
@RestController
public class AlarmController implements BaseController{


    private UserService userService;

    private AlarmService alarmService;

    private ProjectService projectService;

    private int userIdx;

    @Autowired
    public AlarmController(UserService userService, AlarmService alarmService, ProjectService projectService) {
        this.userService = userService;
        this.alarmService = alarmService;
        this.projectService = projectService;
    }

    @GetMapping(value = "/timeline/{userIdx}")
    public List timeline(@PathVariable("userIdx") Integer userIdx,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "count", defaultValue = "20") Integer count) {
        return alarmService.getTimelines(userService.getUser(userIdx), page, count);
    }

    @GetMapping(value = "/alarm/{userIdx}")
    public List alarms(@PathVariable("userIdx") Integer userIdx) {
        return alarmService.getAlarms(userService.getUser(userIdx));
    }


    /*
    To accept invite request
     */
    @GetMapping(value = "/acceptRequest")
    public void acceptRequest(@RequestParam("alarmIdx") Integer alarmIdx, @RequestParam("type") Integer type) {
        Alarm alarm = alarmService.getAlarm(alarmIdx);
        alarm.setIsshow(false);
        if (type == 1)
            alarm.getUser().addProject(alarm.getProject());
        alarmService.save(alarm);
        projectService.save(alarm.getUser(), alarm.getProject());
    }

    /*
    To get invite request
     */
    // socket으로 받아오는거
    @GetMapping(value = "/updateAlarm")
    public Alarm updateAlarm(@RequestParam("userIdx") Integer userIdx) {
        return alarmService.getAlarm(userService.getUser(userIdx));
    }

    @GetMapping(value = "/moreTimeline")
    public List moreSchedule(@RequestParam("page") Integer page, HttpSession session) {
        User user = userService.getUser(userIdx);
        List timeline = alarmService.getTimelines(user, page + 1, 20);
        log.info("REQUEST more timeline");
        if (timeline.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "NO MORE TIMELINE");
        return timeline;
    }

    @Override
    public void setSessionId(int userIdx) {
        this.userIdx=userIdx;
    }
}

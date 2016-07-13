package com.shape.web.controller;

import com.shape.web.entity.*;
import com.shape.web.repository.*;
import com.shape.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/**
 * Handles requests for the calendar.
 */
@Controller
public class CalendarController {
    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    TodolistRepository todolistRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    /*
      To get appropriate file data when user select the date
      */
    /*
    @RequestMapping(value = "/selectDate", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetDate(@RequestParam(value = "projectIdx") Integer projectIdx, @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        List data = new ArrayList();

        File dir;
        String foldername = FileUtil.getFoldername(projectIdx, date);
        dir = new File(foldername);
        if (dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                logger.info(f.getName() + "FILENAME!!!");
                FileDB fd = fs.getByOriginalname(date, f.getName(), foldername);
                if (fd != null) {
                    List<String> temp = new ArrayList<String>();
                    temp.add("<a href='../file?name=" + fd.getStoredname() + "'>" + fd.getOriginalname() + "</a>");
                    temp.add(fd.getUser().getName());
                    temp.add(fd.getDate().toString());
                    data.add(temp);
                } else
                    logger.info(f.getName() + "!!!ERROR!!!");
            }
        }
        map.put("data", data);
        return map;
    }
    */


    /*
   To make schedule
   */

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    @ResponseBody
    public void makeSchedule(@RequestParam("projectIdx") Integer projectIdx, @ModelAttribute("schedule") Schedule schedule) {
        Project project = projectRepository.findOne(projectIdx);
        schedule.setProject(project);
        scheduleRepository.saveAndFlush(schedule);
        Alarm alarm = new Alarm(1, null, null, new Date());
        alarm.setProject(project);
        List<User> lu = userRepository.findByProjects(project);
        for (User u : lu) {
            alarm.setAlarmidx(null);
            logger.info("[USER " + u.getUseridx() + "] Make Alarm");
            alarm.setUser(u);
            alarmRepository.saveAndFlush(alarm);
        }
        logger.info("[ROOM " + projectIdx + "] Make Schedule ");
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.PUT)
    @ResponseBody
    public void updateSchedule(@RequestBody Schedule schedule) {
        Schedule s = scheduleRepository.findOne(schedule.getScheduleidx());
        if (schedule.getStartdate() != null)
            s.setStartdate(schedule.getStartdate());
        if (schedule.getEnddate() != null)
            s.setEnddate(schedule.getEnddate());
        if (schedule.getState() != null)
            s.setState(schedule.getState());
        scheduleRepository.saveAndFlush(s);
        logger.info("modifying schedule");

    }



    /*
        밑에는 기존 Calendar 이를 바꿀예정임
     */



}

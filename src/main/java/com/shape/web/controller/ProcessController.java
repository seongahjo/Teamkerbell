package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.User;
import com.shape.web.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles requests for the whole application processing.
 */
@Controller
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

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
    @Autowired
    FileDBRepository fileDBRepository;

    /*
       To load uploaded Image
       */
    @RequestMapping(value = "/loadImg", method = RequestMethod.GET)
    @ResponseBody
    public void loadImg(@RequestParam(value = "name") String name, HttpServletResponse response) {
        try {
            FileDB file = fileDBRepository.findByStoredname(name);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath() + "/" + file.getOriginalname()));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1)
                byteStream.write(imageByte);
            in.close();
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
            logger.info("SUCCESS LOAD IMG");
        } catch (IOException ioe) {
            // InputStream Error
        } catch (NullPointerException e) {
            // file 존재 안할시
        }
    }


    /*
    To accept invite request
     */
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    @ResponseBody
    public void acceptRequest(@RequestParam("alarmIdx") Integer alarmIdx, @RequestParam("type") Integer type) {
        Alarm alarm = alarmRepository.findOne(alarmIdx);
        alarm.setIsshow(false);
        if (type == 1)
            alarm.getUser().addProject(alarm.getProject());
        userRepository.saveAndFlush(alarm.getUser());
        alarmRepository.saveAndFlush(alarm);
    }

    /*
    To get invite request
     */
    @RequestMapping(value = "/updateAlarm", method = RequestMethod.GET)
    @ResponseBody
    public Map updateAlarm(@RequestParam("userIdx") Integer userIdx) {
        Alarm alarm = alarmRepository.findFirstByContentidAndUserOrderByDateDesc(0, userRepository.findOne(userIdx));
        Map<String, String> data = new HashMap<String, String>();
        if (alarm != null) {
            data.put("alarmidx", String.valueOf(alarm.getAlarmidx()));
            data.put("projectname", alarm.getProject().getName());
            data.put("actorid", alarm.getActor().getId());
        }
        return data;
    }

    @RequestMapping(value = "/moreTimeline", method = RequestMethod.GET)
    @ResponseBody
    public List moreSchedule(@RequestParam("page") Integer page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List timeline = alarmRepository.findByUserOrderByDateDesc(user, new PageRequest(page+1,20));
        logger.info("REQUEST more timeline");
        if(timeline.size()==0)
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        return timeline;
    }
}
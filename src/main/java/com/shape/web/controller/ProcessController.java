package com.shape.web.controller;

import com.shape.web.entity.*;
import com.shape.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    UserService us;

    @Autowired
    ProjectService pjs;

    @Autowired
    TodolistService ts;

    @Autowired
    AlarmService as;

    @Autowired
    FileDBService fs;

    @Autowired
    ScheduleService ss;

    @Autowired
    AppointmentService aps;

    /*
      To accomplish to-do list
      */
    @RequestMapping(value = "/todocheck", method = RequestMethod.GET)
    @ResponseBody
    public void todocheck(@RequestParam(value = "id") Integer id) {
        Todolist todolist = ts.get(id);
        todolist.setOk(!todolist.getOk());
        ts.save(todolist);
    }


    /*
       To load uploaded Image
       */
    @RequestMapping(value = "/loadImg", method = RequestMethod.GET)
    @ResponseBody
    public void loadImg(@RequestParam(value = "name") String name, HttpServletResponse response) {
        try {
            FileDB file = fs.getByStoredname(name);
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
       To find out the date all users choose
    */
    @RequestMapping(value = "/loadTime", method = RequestMethod.GET)
    @ResponseBody
    public List loadTime(@RequestParam("scheduleIdx") Integer scheduleIdx) {
        Schedule schedule = ss.get(scheduleIdx);
        List<String> ls = new ArrayList<String>();
        List<Appointment> la = ss.getAppointments(schedule, 1);
        for (Appointment ap : la)
            ls.add(ap.getDate().toString());
        return ls;
    }

    /*
    To accept invite request
     */
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    @ResponseBody
    public void acceptRequest(@RequestParam("alarmIdx") Integer alarmIdx, @RequestParam("type") Integer type) {
        Alarm alarm = as.get(alarmIdx);
        alarm.setIsshow(false);
        if (type == 1)
            alarm.getUser().addProject(alarm.getProject());
        us.save(alarm.getUser());
        as.save(alarm);
    }

    /*
    To get invite request
     */
    @RequestMapping(value = "/updateAlarm", method = RequestMethod.GET)
    @ResponseBody
    public Map updateAlarm(@RequestParam("userIdx") Integer userIdx) {
        Alarm alarm = us.getOneAlarm(userIdx);
        Map<String, String> data = new HashMap<String, String>();
        if (alarm != null) {
            data.put("alarmidx", String.valueOf(alarm.getAlarmidx()));
            data.put("projectname", alarm.getProject().getName());
            data.put("actorid", alarm.getActor().getId());
        }
        return data;
    }
}
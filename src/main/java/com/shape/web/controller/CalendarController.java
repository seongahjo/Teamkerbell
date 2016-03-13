package com.shape.web.controller;

import com.shape.web.entity.*;
import com.shape.web.service.*;
import com.shape.web.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    UserService us;

    @Autowired
    FileDBService fs;

    @Autowired
    AlarmService as;

    @Autowired
    ProjectService pjs;

    @Autowired
    ScheduleService ss;

    @Autowired
    AppointmentService aps;

    /*
      To get appropriate file data when user select the date
      */
    @RequestMapping(value = "/selectDate", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetDate(@RequestParam(value="projectIdx") Integer projectIdx,@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        List data = new ArrayList();

        File dir;
        String foldername = FileUtil.getFoldername(projectIdx, date);
        dir = new File(foldername);
        if (dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                logger.info(f.getName() + "FILENAME!!!");
                FileDB fd = fs.getByOriginalname(date, f.getName(),foldername);
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

    /*
   To make schedule
   */
    @RequestMapping(value = "/makeSchedule", method = RequestMethod.GET)
    @ResponseBody
    public void makeSchedule(@RequestParam("projectIdx") Integer projectIdx, @ModelAttribute("schedule") Schedule schedule) {
        Project project = pjs.get(projectIdx);
        schedule.setProject(project);
        ss.save(schedule);
        Alarm alarm = new Alarm(1, null, null, new Date());
        alarm.setProject(project);
        List<User> lu = pjs.getUsers(project);
        for (User u : lu) {
            alarm.setAlarmidx(null);
            logger.info(u.getUseridx() + " make");
            alarm.setUser(u);
            as.save(alarm);
        }
        logger.info("Schedule 만듬");
    }

    /*
   To register the date you want to participate & prepare to make meeting when all users make a choice
   */
    @RequestMapping(value = "/makeRegister", method = RequestMethod.GET)
    @ResponseBody
    public void makeRegister(@RequestParam("userIdx") Integer userIdx, @RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
                             @RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate,
                             @ModelAttribute("Appointment") Appointment appointment) {
        Schedule schedule = ss.get(scheduleIdx);
        Project project = schedule.getProject();
        User user = us.get(userIdx);
        boolean check = false; // 모든사람이 체크를 하였는가?
        appointment.setUser(user);
        appointment.setSchedule(schedule);
        List<User> lu = pjs.getUsers(project);

        while (!startdate.after(enddate)) { // startdate 부터 enddate 까지 반복
            appointment.setAppointmentidx(null);
            appointment.setDate(startdate);
            aps.make(appointment, userIdx, scheduleIdx, startdate);
            //appointment가 해당 날짜에 있을경우 생성하지않고 없을경우 생성하는 함수
            if (aps.check(lu.size(), scheduleIdx, startdate)) { // 모든 사람이 체크했는가?
                check = true;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(startdate);
            cal.add(Calendar.DATE, 1); //startdate 날짜에 하루를 더함
            startdate = cal.getTime();
        }
        if (check) {
            schedule.setState(1); // Meeting 날짜 확정
            ss.save(schedule);
        }
        logger.info("appointment 만듬");
    }

    /*
      To make meeting when all users make a choice
   */
    @RequestMapping(value = "/makeMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void makeMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                            @RequestParam("time") String time, @RequestParam("place") String place) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        try {
            java.util.Date d1 = format.parse(time);
            java.sql.Time ppstime = new java.sql.Time(d1.getTime());
            logger.info("Meeting 만들어짐");
            Schedule schedule = ss.get(scheduleIdx);
            schedule.setStartdate(date);
            schedule.setEnddate(date);
            schedule.setTime(ppstime);
            schedule.setPlace(place);
            schedule.setState(2);
            ss.save(schedule);
            ss.updateAm(scheduleIdx, 2, date);
        } catch (ParseException e) {
            // parse fail
        }
    }

    /*
      To find out real participants
   */
    @RequestMapping(value = "/finishMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void finishMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("ids") List<String> ids) {
        Schedule schedule = ss.get(scheduleIdx);
        schedule.setState(3);
        ss.save(schedule);
        List<Appointment> appointments=ss.getAppointments(schedule,2);
        logger.info("Meeting 종료");
        logger.info("ids"+ids);
        for (int i = 0; i < ids.size(); i++)
            for (Appointment a : appointments)
                if (a.getUser().getId().equals(ids.get(i))) {
                    logger.info(a.getDate()+" "+a.getUser().getId()+"vs"+ids.get(i));
                    a.setState(3); // 참가 확정
                    aps.save(a);
                    break;
                }
    }


    /*
 To get meetings
  */
    @RequestMapping(value = "/getEvent", method = RequestMethod.GET)
    @ResponseBody
    public Map getEvent(@RequestParam("projectIdx") Integer projectIdx) {
        Project project = pjs.get(projectIdx);
        SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd-yyyy");
        List<Schedule> ls = pjs.getSchedules(projectIdx); // 프로젝트에 있는 모든 스케쥴들
        Map<String, String> event = new HashMap<String, String>(); //반환할 JSON
        for (Schedule s : ls)
            if (s.getState() >= 2) // 확정된 미팅일경우
                event.put(transFormat.format(s.getStartdate()), "<span> Meeting! <br>" +
                        "To do - " + s.getContent() + "<br>" +
                        "Place - " + s.getPlace() + "<br>" +
                        "Time - " + s.getTime() + "</span>");

        return event;
    }

}

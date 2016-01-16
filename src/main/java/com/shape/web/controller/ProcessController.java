package com.shape.web.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shape.web.entity.*;
import com.shape.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.shape.web.util.FileUtil;

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
    Spring Security로 대체할 예정
     */
    @RequestMapping(value = "/loginok", method = RequestMethod.POST)    //"login.jsp"에서 넘어옴
    public String Loginok(@RequestParam(value = "userId") String userId, @RequestParam(value = "pw") String pw, HttpSession session) {
        User user = us.getById(userId);    //데이터베이스에서 아이디를 조회해서 유저데이터를 받아옴
        if (user != null) {
            logger.info("login : " + user.getId() + "/" + userId);
            if (user.getPw().equals(pw)) {
                session.setAttribute("userIdx", user.getUseridx());
                return "redirect:/dashboard/" + user.getId();    //url="/dashboard/{id}"로 유저아이디를 넘김
            }
        }
        return "redirect:/";
    }

    //회원가입
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam(value = "name") String name,@RequestParam(value = "userId") String userId, @RequestParam(value = "pw") String pw
           ) {
        if (us.getById(userId) == null) {
            us.add(userId, name, pw, "img/default.jpg");
        } else
            return "redirect:/joinus";

        return "login";
    }

    @RequestMapping(value = "/makeroom", method = RequestMethod.GET)    //프로젝트 개설
    public String MakeRoom(@RequestParam(value = "name") String name, HttpSession session) {
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        Project project = new Project(name, userIdx, "");
        us.addProject(userIdx, project);
        return "redirect:/projectmanager";
    }

    @RequestMapping(value = "/deleteroom", method = RequestMethod.GET)    //프로젝트 삭제
    public String deleteRoom(@RequestParam(value = "projectIdx") Integer projectIdx, HttpSession session) {
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        us.deleteProject(projectIdx);
        return "redirect:/projectmanager";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    @ResponseBody
    public HashMap searchUser(@RequestParam(value = "userId") String userId, HttpSession session) {
        logger.info("Search Member");
        Integer projectIdx = (Integer) session.getAttribute("room");
        Project project = pjs.get(projectIdx);
        User user = us.getById(userId);
        logger.info(project.getName());
        List<Project> sp = us.getProjects(user); // 유저가 참가중인 프로젝트
        for (Project p : sp) {
            if (p.getProjectidx() == project.getProjectidx())
                return null;
        }
        HashMap<String, String> Value = new HashMap<String, String>();
        Value.put("userId", user.getId());
        Value.put("name", user.getName());
        Value.put("img", user.getImg());
        return Value;
    }


    @RequestMapping(value = "/inviteUser", method = RequestMethod.GET)
    @ResponseBody
    public String InviteMember(@RequestParam(value = "userId") String userId, HttpSession session) {
        logger.info("Invite Member");
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        Integer projectIdx = (Integer) session.getAttribute("room");
        User actor = us.get(userIdx);
        User user = us.getById(userId);
        Project project = pjs.get(projectIdx);

        Alarm alarm = new Alarm(0, null, null, new Date());
        alarm.setUser(user);
        alarm.setActor(actor);
        alarm.setProject(project);
        as.save(alarm);
        return String.valueOf(userIdx);
    }

    @RequestMapping(value = "/todocheck", method = RequestMethod.GET)
    @ResponseBody
    public void IsTodolist(@RequestParam(value = "id") Integer id) throws Exception {
        Todolist todolist = ts.get(id);
        todolist.setOk(!todolist.getOk());
        ts.save(todolist);
    }


    //Calendar 데이터받기
    @RequestMapping(value = "/selectDate", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, HttpSession session) {
        Integer project_id = (Integer) session.getAttribute("room");
        Map<String, Object> map = new HashMap<String, Object>();
        List data = new ArrayList();
        List<String> temp = new ArrayList<String>();
        File dir;
        String foldername = FileUtil.getFoldername(project_id, date);
        dir = new File(foldername);

        if (dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                FileDB fd = fs.getByOriginalname(f.getName());
                if (fd != null) {
                    temp.add("<a href='download?filename=" + fd.getStoredname() + "'>" + fd.getOriginalname() + "</a>");
                    temp.add(fd.getUser().getName());
                    temp.add(fd.getDate().toString());
                    data.add(temp);
                } else {
                    logger.info(f.getName() + "!!!ERROR!!!");
                }
            }
        }
/* String content = null;
       ArrayList<String> contents = new ArrayList<String>();
        File file;
       ArrayList<String> filenames = new ArrayList<String>();
        String filename;
          HashMap<Integer, HashMap<String, String>> hm = new HashMap<Integer, HashMap<String, String>>();
      */
        // filename = foldername + "/minute.txt";
//   int size = 0;
       /*
        file = new File(filename);
        if (file.exists()) {
            BufferedReader f = new BufferedReader(new FileReader(filename));
            content = f.readLine();
            contents.add(content);
            f.close();
        } else {
            contents.add("");
            logger.info("파일 존재안함");
        }*/

        map.put("data", data);

        return map;
    }

    @RequestMapping(value = "/loadImg", method = RequestMethod.GET)
    @ResponseBody
    public void loadImg(@RequestParam("name") String name, HttpServletResponse response) {
        FileDB file = fs.getByStoredname(name);
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath() + "/" + file.getOriginalname()));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1) {
                byteStream.write(imageByte);
            }
            in.close();
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
        } catch (IOException ioe) {
        }
    }


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

    @RequestMapping(value = "/makeTodolist", method = RequestMethod.GET)
    public String makeTodolist(@RequestParam("projectIdx") Integer projectIdx, @RequestParam("userId") String userId, @ModelAttribute("todolist") Todolist todolist) {
        Project project = pjs.get(projectIdx);
        User user = us.getById(userId);
        todolist.setProject(project);
        todolist.setUser(user);
        ts.save(todolist);
        logger.info("todolist 만듬");
        return "redirect:/chat?projectIdx=" + projectIdx;
    }

    @RequestMapping(value = "/makeRegister", method = RequestMethod.GET)
    @ResponseBody
    public void makeRegister(@RequestParam("userIdx") Integer userIdx, @RequestParam("scheduleIdx") Integer scheduleIdx,
                             @RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate, @RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate,
                             @ModelAttribute("Appointment") Appointment appointment) {
        Schedule schedule = ss.get(scheduleIdx);
        Project project = schedule.getProject();
        User user = us.get(userIdx);
        boolean check = false;
        appointment.setUser(user);
        appointment.setSchedule(schedule);
        List<User> lu = pjs.getUsers(project);

        while (!startdate.after(enddate)) {
            appointment.setAppointmentidx(null);
            appointment.setDate(startdate);
            aps.make(appointment, userIdx, scheduleIdx, startdate);
            if (aps.check(lu.size(), scheduleIdx, startdate))
                check = true;
            logger.info(lu.size() + "명 있음");
            Calendar cal = Calendar.getInstance();
            cal.setTime(startdate);
            cal.add(Calendar.DATE, 1);
            startdate = cal.getTime();
        }
        if (check) {
            schedule.setState(1);
            ss.save(schedule);
        }
        logger.info("appointment 만듬");


    }

    @RequestMapping(value = "/loadTime", method = RequestMethod.GET)
    @ResponseBody
    public List loadTime(@RequestParam("scheduleIdx") Integer scheduleIdx) {
        Schedule schedule = ss.get(scheduleIdx);

        List<String> ls = new ArrayList<String>();
        List<Appointment> la = ss.getAppointments(schedule,1);
        for (Appointment ap : la) {
            ls.add(ap.getDate().toString());
        }
        return ls;
    }

    @RequestMapping(value = "/makeMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void makeMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                            @RequestParam("time") String time, @RequestParam("place") String place) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        java.util.Date d1 = format.parse(time);
        java.sql.Time ppstime = new java.sql.Time(d1.getTime());
        Schedule schedule = ss.get(scheduleIdx);
        schedule.setStartdate(date);
        schedule.setEnddate(date);
        schedule.setTime(ppstime);
        schedule.setPlace(place);
        schedule.setState(2);
        ss.save(schedule);
        logger.info("makeMeeting = ",date);
        ss.updateAm(scheduleIdx, 2, date);
    }

    @RequestMapping(value = "/finishMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void finishMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("ids") List<String> ids) {
        Schedule schedule = ss.get(scheduleIdx);
        schedule.setState(3);
        logger.info("TEST START");
        Set<Appointment> appointments = schedule.getAppointments();
        logger.info(ids+ "WHAT");
        for (int i = 0; i < ids.size(); i++) {
            for (Appointment a : appointments) {
                logger.info("WHAT?");
                if (a.getUser().getId().equals(ids.get(i))) {
                    logger.info("SAVE GOOD!");
                    a.setState(3);
                    ss.save(schedule);
                    break;
                }
            }
        }

    }


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

    @RequestMapping(value = "/getEvent", method = RequestMethod.GET)
    @ResponseBody
    public Map getEvent(@RequestParam("projectIdx") Integer projectIdx) {
        Project project = pjs.get(projectIdx);
        SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd-yyyy");
        List<Schedule> ls = pjs.getSchedules(projectIdx);
        Map<String, String> event = new HashMap<String, String>();
        for (Schedule s : ls) {
            if (s.getState() >= 2) {
                event.put(transFormat.format(s.getStartdate()), "<span> Meeting! <br>" +
                        "To do - " + s.getContent() + "<br>" +
                        "Place - " + s.getPlace() + "<br>" +
                        "Time - " + s.getTime() + "</span>");
            }
        }
        return event;
    }


}
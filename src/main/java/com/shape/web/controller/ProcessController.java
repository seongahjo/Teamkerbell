package com.shape.web.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Handles requests for the application processing.
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
    To login Spring Security로 대체할 예정
     */
    @RequestMapping(value = "/loginok", method = RequestMethod.GET)    //"login.jsp"에서 넘어옴
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

    /*
    To register
    */
   /* @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam(value = "name") String name, @RequestParam(value = "userId") String userId, @RequestParam(value = "pw") String pw) {
        if (us.getById(userId) == null)
            us.add(userId, name, pw, "img/default.jpg");
        else
            return "redirect:/joinus";
        return "login";
    }*/

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@ModelAttribute("user")User user, HttpServletRequest HSrequest)  {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) HSrequest;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        String filePath = "img";
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        Pattern pattern = Pattern.compile("\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());

            if (!multipartFile.isEmpty()) {
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                Matcher m = pattern.matcher(originalFileExtension);
                if (m.matches()) {
                    file = new File(filePath + "/" + originalFileName);
                    try {
                        multipartFile.transferTo(file);
                        user.setImg(filePath+"/"+originalFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info(filePath + "/" + originalFileName + " UPLOAD FINISHED!");
                    return "ok";
                }
            }

        }
        return "ok";
    }



    /*
    To make project room
    */
    @RequestMapping(value = "/room", method = RequestMethod.POST)    //프로젝트 개설
    public String MakeRoom(@RequestParam(value = "name") String name, HttpSession session) {
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        Project project = new Project(name, userIdx, "");
        us.addProject(userIdx, project);
        return "redirect:/projectmanager";
    }

    /*
    To delete project room
    */
    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.DELETE)    //프로젝트 삭제
    @ResponseBody
    public void deleteRoom(@PathVariable("projectIdx") Integer projectIdx, HttpSession session) {
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        us.deleteProject(projectIdx);
    }

    /*
       To find out invited user
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    @ResponseBody
    public HashMap searchUser(@RequestParam(value = "userId") String userId, HttpSession session) {
        logger.info("Search Member");
        Integer projectIdx = (Integer) session.getAttribute("room");
        Project project = pjs.get(projectIdx);
        User user = us.getById(userId);
        logger.info(project.getName());
        List<Project> lp = us.getProjects(user); // 유저가 참가중인 프로젝트
        for (Project p : lp)
            if (p.getProjectidx() == project.getProjectidx())
                return null;

        HashMap<String, String> Value = new HashMap<String, String>();
        Value.put("userId", user.getId());
        Value.put("name", user.getName());
        Value.put("img", user.getImg());
        return Value;
    }

    /*
       To invite user to project room
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.GET)
    @ResponseBody
    public String InviteMember(@RequestParam(value = "userId") String userId, HttpSession session) {
        logger.info("Invite Member");
        Integer userIdx = (Integer) session.getAttribute("userIdx");
        Integer projectIdx = (Integer) session.getAttribute("room");
        User actor = us.get(userIdx); // 초대한 사람
        User user = us.getById(userId); // 초대받은 사람
        Project project = pjs.get(projectIdx); // 초대받은 프로젝트
        Alarm alarm = new Alarm(0, null, null, new Date());
        alarm.setUser(user);
        alarm.setActor(actor);
        alarm.setProject(project);
        as.save(alarm); //알람 생성
        return String.valueOf(user.getUseridx());
    }

    /*
      To accomplish to-do list
      */
    @RequestMapping(value = "/todocheck", method = RequestMethod.GET)
    @ResponseBody
    public void IsTodolist(@RequestParam(value = "id") Integer id) throws Exception {
        Todolist todolist = ts.get(id);
        todolist.setOk(!todolist.getOk());
        ts.save(todolist);
    }


    /*
       To get appropriate file data when user select the date
       */
    @RequestMapping(value = "/selectDate", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, HttpSession session) {
        Integer project_id = (Integer) session.getAttribute("room");
        Map<String, Object> map = new HashMap<String, Object>();
        List data = new ArrayList();

        File dir;
        String foldername = FileUtil.getFoldername(project_id, date);
        dir = new File(foldername);
        if (dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                logger.info(f.getName() + "FILENAME!!!");
                FileDB fd = fs.getByOriginalname(date, f.getName());
                if (fd != null) {
                    List<String> temp = new ArrayList<String>();
                    temp.add("<a href='file?name=" + fd.getStoredname() + "'>" + fd.getOriginalname() + "</a>");
                    temp.add(fd.getUser().getName());
                    temp.add(fd.getDate().toString());
                    data.add(temp);
                } else
                    logger.info(f.getName() + "!!!ERROR!!!");
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

    /*
       To load uploaded Image
       */
    @RequestMapping(value = "/loadImg", method = RequestMethod.GET)
    @ResponseBody
    public void loadImg(@RequestParam("name") String name, HttpServletResponse response) {
        FileDB file = fs.getByStoredname(name);
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath() + "/" + file.getOriginalname()));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1)
                byteStream.write(imageByte);
            in.close();
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
        } catch (IOException ioe) {
        }
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
    To make to-do list
    */
    @RequestMapping(value = "/makeTodolist", method = RequestMethod.GET)
    public String makeTodolist(@RequestParam("projectIdx") Integer projectIdx, @RequestParam("userId") String userId, @ModelAttribute("todolist") Todolist todolist) {
        Project project = pjs.get(projectIdx);
        User user = us.getById(userId);
        todolist.setProject(project); // todolist가 어디 프로젝트에서 생성되었는가
        todolist.setUser(user); // todolist가 누구것인가
        ts.save(todolist); // todolist 생성
        logger.info("todolist 만듬");
        return "redirect:/chat?projectIdx=" + projectIdx;
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
                break;
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
       To make meeting when all users make a choice
    */
    @RequestMapping(value = "/makeMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void makeMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                            @RequestParam("time") String time, @RequestParam("place") String place) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
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
    }

    /*
      To find out real participants
   */
    @RequestMapping(value = "/finishMeeting", method = RequestMethod.GET)
    @ResponseBody
    public void finishMeeting(@RequestParam("scheduleIdx") Integer scheduleIdx, @RequestParam("ids") List<String> ids) {
        Schedule schedule = ss.get(scheduleIdx);
        schedule.setState(3);
        Set<Appointment> appointments = schedule.getAppointments();
        logger.info("Meeting 종료");
        for (int i = 0; i < ids.size(); i++)
            for (Appointment a : appointments)
                if (a.getUser().getId().equals(ids.get(i))) {
                    a.setState(3); // 참가 확정
                    ss.save(schedule);
                    break;
                }
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
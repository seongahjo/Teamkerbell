package com.shape.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.shape.web.entity.*;
import com.shape.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shape.web.util.FileUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    //메뉴 컨트롤러
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService us;
    @Autowired
    ProjectService pjs;
    @Autowired
    TodolistService ts;
    @Autowired
    AlarmService as;
    @Autowired
    MinuteService ms;
    @Autowired
    ScheduleService ss;

    /**
     * Simply selects the home view to render by returning its name.
     */


    @RequestMapping(value = "/", method = RequestMethod.GET)    //시작부
    public String Home(Locale locale, Model model) {

        return "login";
    }

    @RequestMapping(value = "/joinus", method = RequestMethod.GET)
    public ModelAndView JoinUs() {
        ModelAndView mv = new ModelAndView("/joinus");    //ModelAndView : 컨트롤러의 처리 결과를 보여줄 뷰와 뷰에 전달할 값을 저장
        return mv;
    }

    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
    public ModelAndView UserInfo(@PathVariable("userId") String userId) {
        User user = us.getById(userId);    //유저 아이디로 유저레코드 검색
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트를 반환

        ModelAndView mv = new ModelAndView("/userInfo");    //ModelAndView : 컨트롤러의 처리 결과를 보여줄 뷰와 뷰에 전달할 값을 저장
        mv.addObject("user", user);
        mv.addObject("projects", lpj);
        return mv;
    }

    @RequestMapping(value = "/dashboard/{userId}", method = RequestMethod.GET)
    public ModelAndView Dashboard(@PathVariable("userId") String userId, HttpSession session) {
        User user = us.getById(userId);    //유저 아이디로 유저레코드 검색
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트를 반환
        List<Alarm> tlla = us.getTimeline(user); // 타임라인 리스트를 반환
        List<Todolist> lt = us.getTodolist(user); // 투두리스트 리스트를 반환
        List<Schedule> ls = us.getScheudles(user); // 스케쥴 리스트를 반환
        List<Alarm> la = us.getAlarms(user.getUseridx()); // 알람 리스트를 반환

        ModelAndView mv = new ModelAndView("/dashboard");
        mv.addObject("user", user);
        mv.addObject("timeline", tlla);
        mv.addObject("alarm", la);
        mv.addObject("projects", lpj);
        mv.addObject("todolist", lt);
        mv.addObject("schedules", ls);
        return mv;
    }

    @RequestMapping(value = "/chat/{projectIdx}", method = RequestMethod.GET)
    public ModelAndView Chat(@PathVariable("projectIdx") Integer projectIdx, HttpSession session)  {
        int userIdx = (Integer) session.getAttribute("userIdx");
        User user = us.get(userIdx); // 유저 객체 반환
        Project project = pjs.get(projectIdx); // 프로젝트 객체 반환
        List<Minute> lm = pjs.getMinutes(project); // 회의록 객체 반환
        List<Alarm> la = us.getAlarms(userIdx); // 알람 리스트를 반환
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트 반환
        List<User> lu = pjs.getUsers(project); // 유저 리스트 반환
        List<FileDB> img = pjs.getImgs(project); // 파일디비 리스트중 이미지 리스트 반환
        session.setAttribute("room", projectIdx);

        File file;
        String foldername = FileUtil.getFoldername(projectIdx, null);
        //folder name 받기
        file = new File(foldername);
        if (!file.exists())
            if (file.mkdirs())
                logger.info("폴더 새로 생성");

        ModelAndView mv = new ModelAndView("/project");
        mv.addObject("projects", lpj);
        mv.addObject("users", lu);
        mv.addObject("user", user);
        mv.addObject("alarm", la);
        mv.addObject("minutes", lm);
        mv.addObject("project", project);
        mv.addObject("img", img);
        return mv;
    }

    @RequestMapping(value = "/calendar/{projectIdx}", method = RequestMethod.GET)
    public ModelAndView calendar(@PathVariable("projectIdx") Integer projectIdx, HttpSession session)  {
        int userIdx = (Integer) session.getAttribute("userIdx"); // user id 받아옴
        User user = us.get(userIdx); // 유저 객체 반환
        Project project = pjs.get(projectIdx); // 프로젝트 객체 반환
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트 객체 반환
        List<Schedule> ls = pjs.getSchedules(projectIdx); // 스케쥴 객체 반환
        List<User> lu = pjs.getUsers(project); // 유저 객체 반환
        List<Alarm> la = us.getAlarms(userIdx); // 알람 리스트를 반환

        ModelAndView mv = new ModelAndView("/calendar");
        mv.addObject("user", user);
        mv.addObject("schedules", ls);
        mv.addObject("projects", lpj);
        mv.addObject("project", project);
        mv.addObject("users", lu);
        mv.addObject("alarm", la);
        return mv;
    }

    @RequestMapping(value = "/projectmanager", method = RequestMethod.GET)
    public ModelAndView manager(HttpSession session)  {
        int userIdx = (Integer) session.getAttribute("userIdx"); // 세션에서 user id 받아옴
        User user = us.get(userIdx); // 유저 객체 반환
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트 객체 반환

        ModelAndView mv = new ModelAndView("/EditPJ");
        mv.addObject("user", user);
        mv.addObject("projects", lpj);
        return mv;
    }

    @RequestMapping(value = "/document/{projectIdx}", method = RequestMethod.GET)
    public ModelAndView document(@PathVariable("projectIdx") Integer projectIdx,HttpSession session)  {
        int userIdx = (Integer) session.getAttribute("userIdx"); // 세션에서 user id 받아옴
        User user = us.get(userIdx); // 유저 객체 반환
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트 객체 반환
        Project project = pjs.get(projectIdx); // 프로젝트 객체 반환
        List<Schedule> ls = pjs.getSchedules(projectIdx); // 스케쥴 객체 반환
        List<User> lu = pjs.getUsers(project); // 유저 객체 반환
        List<Alarm> la = us.getAlarms(userIdx); // 알람 리스트를 반환
        List<Todolist> lt = pjs.getTodolists(projectIdx); // 투두리스트 리스트를 반환

        ModelAndView mv = new ModelAndView("/document");
        mv.addObject("user", user);
        mv.addObject("schedules", ls);
        mv.addObject("projects", lpj);
        mv.addObject("project", project);
        mv.addObject("users", lu);
        mv.addObject("alarm", la);
        mv.addObject("todolist", lt);
        return mv;
    }

    @RequestMapping(value = "/filemanager/{projectIdx}", method = RequestMethod.GET)
    public ModelAndView fileManager(@PathVariable("projectIdx") Integer projectIdx,HttpSession session)  {
        Project project = pjs.get(projectIdx);
        int userIdx = (Integer) session.getAttribute("userIdx"); // 세션에서 user id 받아옴
        User user = us.get(userIdx); // 유저 객체 반환
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트 객체 반환
        List<FileDB> lfd = pjs.getFiles(project);

        ModelAndView mv = new ModelAndView("/filemanager");
        mv.addObject("user", user);
        mv.addObject("projects", lpj);
        mv.addObject("files",lfd);
        return mv;
    }

    @RequestMapping(value = "/courseInfo/{userId}", method = RequestMethod.GET)
    public ModelAndView CourseInfo(@PathVariable("userId") String userId, HttpSession session) {
        User user = us.getById(userId);    //유저 아이디로 유저레코드 검색
        List<Project> lpj = us.getProjects(user); // 프로젝트 리스트를 반환

        ModelAndView mv = new ModelAndView("/courseInfo");
        mv.addObject("user", user);
        mv.addObject("projects", lpj);
        return mv;
    }
}

package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.Todolist;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.TodolistService;
import com.shape.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/**
 * Handles requests for the project.
 */
@Controller
public class ProjectController {
    @Autowired
    UserService us;

    @Autowired
    ProjectService pjs;

    @Autowired
    AlarmService as;

    @Autowired
    TodolistService ts;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

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
    To make to-do list
    */
    @RequestMapping(value = "/makeTodolist", method = RequestMethod.GET)
    @ResponseBody
    public String makeTodolist(@RequestParam("projectIdx") Integer projectIdx, @RequestParam("userId") String userId, @ModelAttribute("todolist") Todolist todolist) {
        Project project = pjs.get(projectIdx);
        User user = us.getById(userId);
        todolist.setProject(project); // todolist가 어디 프로젝트에서 생성되었는가
        todolist.setUser(user); // todolist가 누구것인가
        ts.save(todolist); // todolist 생성
        logger.info("todolist 만듬");
        return "redirect:/chat?projectIdx=" + projectIdx;
    }


}

package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.*;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);



    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AlarmRepository alarmRepository;

    /*
    RESTFUL DOCUMENTATION
    ROOM
        POST : CREATE (PROJECT)
        DELETE : DELETE

    InviteUser
        POST : SEARCH
        GET : INVITE
     */
    /*
    To make project room
    */
    @RequestMapping(value = "/room", method = RequestMethod.POST)    //프로젝트 개설
    public String MakeRoom(@RequestParam(value = "name") String name, Authentication authentication) {
        User user = userRepository.findById(authentication.getName()); //유저 객체 반환
        Integer userIdx = user.getUseridx();
        Project project = new Project(name, userIdx, "");
        user.addProject(project);
        project.addUser(user);
        projectRepository.saveAndFlush(project);
        userRepository.saveAndFlush(user);
        return "redirect:/projectmanager";
    }

    /*
    To delete project room
    */
    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.DELETE)    //프로젝트 삭제
    @ResponseBody
    public void deleteRoom(@PathVariable("projectIdx") Integer projectIdx) {
        projectRepository.delete(projectIdx);
    }

    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.PUT)    //프로젝트 삭제
    @ResponseBody
    public void updadeRoom(@PathVariable("projectIdx") Integer projectIdx) {
        Project project = projectRepository.findOne(projectIdx);
        project.setProcessed(false);
        projectRepository.saveAndFlush(project);
    }

    /*
       To find out invited user
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    @ResponseBody
    public HashMap searchUser(@RequestParam(value = "userId") String userId,
                              @RequestParam("projectIdx") Integer projectIdx) {
        logger.info("Search Member");
        Project project = projectRepository.findOne(projectIdx);
        User user = userRepository.findById(userId);
        logger.info(project.getName());
        List<Project> lp = projectRepository.findByUsers(user); // 유저가 참가중인 프로젝트
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
    public String InviteMember(@RequestParam(value = "userId") String userId,
                               @RequestParam("projectIdx") Integer projectIdx,
                               Authentication authentication) {
        logger.info("Invite Member");
        User actor = userRepository.findById(authentication.getName()); //초대한 사람
        User user = userRepository.findById(userId); // 초대받은 사람
        Project project = projectRepository.findOne(projectIdx); // 초대받은 프로젝트
        Alarm alarm = new Alarm(0, null, null, new Date());
        alarm.setUser(user);
        alarm.setActor(actor);
        alarm.setProject(project);
        alarmRepository.saveAndFlush(alarm); //알람 생성
        return String.valueOf(user.getUseridx());
    }


}

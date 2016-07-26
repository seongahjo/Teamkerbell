package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    AlarmService alarmService;
    @Autowired
    ProjectService projectService;
    /*
    RESTFUL DOCUMENTATION
    ROOM
        POST : CREATE (PROJECT)
        DELETE : DELETE

    InviteUser
        POST : SEARCH
        GET : INVITE
     */

    @RequestMapping(value = "/room", method = RequestMethod.GET)    //프로젝트 개설
    @ResponseBody
    public List getRoom(@RequestParam(value = "page") Integer page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Project> projects = projectRepository.findByUsers(user, new PageRequest(page,5));
        logger.info("room paging");
        return projects;
    }

    /*
    To make project room
    */
    @RequestMapping(value = "/room", method = RequestMethod.POST)    //프로젝트 개설
    public String MakeRoom(@RequestParam(value = "name") String name, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userIdx = user.getUseridx();
        Project project = new Project(name, userIdx, "");
        user.addProject(project);
        project.addUser(user);
        projectService.save(project,user);
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
    public Map searchUser(@RequestParam(value = "userId") String userId,
                          @RequestParam("projectIdx") Integer projectIdx)  {
        logger.info("Search Member");
        Project project = projectRepository.findOne(projectIdx);
        User user = userRepository.findById(userId);
        if(user==null){
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        logger.info(project.getName());
        List<Project> lp = projectRepository.findByUsers(user); // 유저가 참가중인 프로젝트
        for (Project p : lp)
            if (p.getProjectidx() == project.getProjectidx()) {
                throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }

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
                               HttpSession session) {
        logger.info("Invite Member");
        User actor = (User) session.getAttribute("user"); //초대한 사람
        User user = userRepository.findById(userId); // 초대받은 사람
        Project project = projectRepository.findOne(projectIdx); // 초대받은 프로젝트
        Alarm alarm = new Alarm(0, null, null, new Date(),project,user,actor);
        alarmService.create(alarm);
        return String.valueOf(user.getUseridx());
    }


}

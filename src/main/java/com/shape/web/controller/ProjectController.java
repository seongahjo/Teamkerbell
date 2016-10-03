package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/**
 * Handles requests for the project.
 */
@Slf4j
@RestController
public class ProjectController {
    

    @Autowired
    AlarmService alarmService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    /*
    RESTFUL DOCUMENTATION
    ROOM
        POST : CREATE (PROJECT)
        DELETE : DELETE

    InviteUser
        POST : SEARCH
        GET : INVITE
     */

    @RequestMapping(value="/room/{userIdx}/user",method=RequestMethod.GET)
    public List getRooms(@PathVariable(value="userIdx") Integer userIdx,
                         @RequestParam(value="page",defaultValue="0") Integer page,
                         @RequestParam(value="count", defaultValue="15") Integer count){
        return projectService.getProjects(userService.getUser(userIdx),page,count);
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)    //프로젝트 반환
    public List getRoom(@RequestParam(value = "page", defaultValue="0") Integer page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Project> projects = projectService.getProjects(user, page,5);
        log.info("room paging");
        return projects;
    }

    /*
    To make project room
    */
    @RequestMapping(value = "/room", method = RequestMethod.POST)    //프로젝트 개설
    public void MakeRoom(@RequestParam(value = "name") String name, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userIdx = user.getUseridx();
        Project project = new Project(name, userIdx, "");
        user.addProject(project);
        project.addUser(user);
        projectService.save(user,project);
    }

    /*
    To delete project room
    */
    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.DELETE)    //프로젝트 삭제
    public void deleteRoom(@PathVariable("projectIdx") Integer projectIdx) {
        projectService.delete(projectIdx);
    }

    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.PUT)    //프로젝트 삭제
    public void updadeRoom(@PathVariable("projectIdx") Integer projectIdx,HttpSession session) {
        User u=(User) session.getAttribute("user");
        Project project = projectService.getProject(projectIdx);
        project.setProcessed(false);
        projectService.save(u,project);
    }

    /*
       To find out invited user
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    public User searchUser(@RequestParam(value = "userId") String userId,
                          @RequestParam("projectIdx") Integer projectIdx)  {
        log.info("Search Member");
        Project project = projectService.getProject(projectIdx);
        User user = userService.getUser(userId);
        if(user==null){
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        log.info(project.getName());
        List<Project> lp = projectService.getProjects(user); // 유저가 참가중인 프로젝트
        lp.forEach(p->{
            if (p.getProjectidx() == project.getProjectidx()) {
                throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        });
      return user;
    }

    /*
       To invite user to project room
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.GET)
    public String InviteMember(@RequestParam(value = "userId") String userId,
                               @RequestParam("projectIdx") Integer projectIdx,
                               HttpSession session) {
        log.info("Invite Member");
        User actor = (User) session.getAttribute("user"); //초대한 사람
        User user = userService.getUser(userId); // 초대받은 사람
        Project project = projectService.getProject(projectIdx); // 초대받은 프로젝트
        Alarm alarm = new Alarm(0, null, null, new Date(),project,user,actor);
        alarmService.save(alarm);
        return String.valueOf(user.getUseridx());
    }


}

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
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/room/{userIdx}/user", method = RequestMethod.GET)
    public List getRooms(@PathVariable(value = "userIdx") Integer userIdx,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "count", defaultValue = "15") Integer count) {
        return projectService.getProjects(userService.getUser(userIdx), page, count);
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)    //프로젝트 반환
    public ResponseEntity getRoom(@RequestParam(value = "page", defaultValue = "0") Integer page, HttpSession session) {
        Integer useridx =(Integer) session.getAttribute("useridx");
        User user = userService.getUser(useridx);
        List<Project> projects = projectService.getProjects(user, page, 5);
        if(projects.size()==0)
            return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(projects);
    }

    /*
    To make project room
    */

    /*
    To delete project room
    */
    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.DELETE)    //프로젝트 삭제
    public void deleteRoom(@PathVariable("projectIdx") Integer projectIdx, HttpSession session) {
        Project project = projectService.getProject(projectIdx);
        project.getUsers().stream().forEach(tempU -> {
            if (tempU.getProjects().remove(project))
                projectService.delete(tempU, projectIdx);
        });

    }

    @RequestMapping(value = "/room/{projectIdx}", method = RequestMethod.PUT)    //프로젝트 업데이트
    public void updadeRoom(@PathVariable("projectIdx") Integer projectIdx, HttpSession session) {

        Project project = projectService.getProject(projectIdx);
        log.info("[" + projectIdx + "]Project Finished");
        project.getUsers().stream().forEach((tempU) -> // 프로젝트에 속한 각 유저
                    tempU.getProjects().stream().anyMatch(p -> {
                        if (p.equals(project)) {
                            p.setProcessed(false);
                            projectService.save(tempU, p);
                            return true;
                        }
                        return false;
                    })

        );


    }

    /*
       To find out invited user
       */
    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    public User searchUser(@RequestParam(value = "userId") String userId,
                           @RequestParam("projectIdx") Integer projectIdx) {
        log.info("Search Member");
        Project project = projectService.getProject(projectIdx);
        User user = userService.getUser(userId);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        log.info(project.getName());
        List<Project> lp = projectService.getProjects(user); // 유저가 참가중인 프로젝트
        lp.forEach(p -> {
            if (p.getProjectidx() == project.getProjectidx()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
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
        Integer useridx =(Integer) session.getAttribute("useridx");
        User actor = userService.getUser(useridx); //초대한 사람
        User user = userService.getUser(userId); // 초대받은 사람
        Project project = projectService.getProject(projectIdx); // 초대받은 프로젝트
        Alarm alarm = new Alarm(0, null, null, new Date(), project, user, actor);
        alarmService.save(alarm);
        return String.valueOf(user.getUseridx());
    }


}

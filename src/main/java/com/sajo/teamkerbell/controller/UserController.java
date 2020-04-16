package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.RegisterServiceFacade;
import com.sajo.teamkerbell.service.UserService;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/*
  Handles requests for the User .
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RegisterServiceFacade registerServiceFacade;


    @GetMapping(value = "/user/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

//    @GetMapping(value = "/user/{userId}/projects")    //프로젝트 반환
//    public ResponseEntity<List<Project>> getProjectsFromUser(
//            @PathVariable("userId") Integer userId,
//            @RequestParam(value = "page", defaultValue = "0") Integer page) {
//        List<Project> projects = projectService.getProjects(user, page, 5);
//        if (projects.isEmpty())
//            return ResponseEntity.badRequest().body(null);
//        return ResponseEntity.ok(projects);
//    }

    @PostMapping(value = "/user")
    public ResponseEntity<String> register(@ModelAttribute("user") @Valid UserVO userVo, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            log.info("Register Error");
            return ResponseEntity.badRequest().body("Error! ");
        }
        if (!userService.isExist(userVo))
            return ResponseEntity.badRequest().body("Already Used Id");
        User user = registerServiceFacade.registerUser(file, userVo);
        log.info("Register Success " + user.getName());
        return ResponseEntity.ok("success");
    }
}

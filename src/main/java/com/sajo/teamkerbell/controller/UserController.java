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
import java.util.List;

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
        return userService.getUserByUserId(userId);
    }

    @GetMapping(value = "/project/{projectId}/users")
    public List<User> getUsersFromProject(@PathVariable("projectId") Integer projectId,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return userService.getUsersFromProject(projectId, page, size);
    }


    @PostMapping(value = "/user")
    public ResponseEntity<String> register(@ModelAttribute("user") @Valid UserVO userVo,
                                           BindingResult result,
                                           @RequestParam("file") MultipartFile file) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body("Error! ");

        if (userService.isExist(userVo))
            return ResponseEntity.badRequest().body("Already Used Id");

        User user = registerServiceFacade.registerUser(file, userVo);
        log.info("Register Success " + user.getName());
        return ResponseEntity.ok("success");
    }

    /*
      To find out invited user
      */
    @PostMapping(value = "/project/{projectId}/invite/{userId}")
    public ResponseEntity<User> searchUser(@PathVariable("userId") Integer userId,
                                           @PathVariable("projectId") Integer projectId) {
        User user = userService.searchProjectCandidate(userId, projectId);

        if (user == null)
            throw new IllegalStateException("Already participated");

        return ResponseEntity.ok(user);
    }
}

package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.service.ProjectService;
import com.sajo.teamkerbell.service.RegisterServiceFacade;
import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.Role;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.UserService;
import com.sajo.teamkerbell.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/*
  Handles requests for the User .
 */
@Slf4j
@RestController
public class UserController {

    private UserService userService;

    private ProjectService projectService;

    private RegisterServiceFacade registerServiceFacade;

    public UserController(UserService userService, ProjectService projectService, RegisterServiceFacade registerServiceFacade) {
        this.userService = userService;
        this.projectService = projectService;
        this.registerServiceFacade = registerServiceFacade;
    }

    @GetMapping(value = "/user/{userIdx}")
    public User getUser(@PathVariable("userIdx") Integer userIdx) {
        return userService.getUser(userIdx);
    }

    @GetMapping(value = "/user/{projectIdx}/project")
    public List getUsers(@PathVariable("projectIdx") Integer projectIdx,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return userService.getUsersByProject(projectService.getProject(projectIdx));
    }

    @PostMapping(value = "/updateuser")
    public ResponseEntity updateUser(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        User u = userService.getUser(user.getId());
        u.setName(user.getName());
        u.setPw(user.getPw());
        //  파일 관련 처리
        try {
            String filePath = "img";
            String originalFileName = file.getOriginalFilename(); // 파일 이름
            String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf('.')); // 파일 확장자
            String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
            FileDB filedb = new FileDB(storedFileName, originalFileName, filePath, "img", null);
            registerServiceFacade.registerUser(file, filePath, filedb, user);
            return ResponseEntity.ok("UPDATE SUCCESS");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("FAILED");
        } catch (StringIndexOutOfBoundsException e) {
            if (user.getImg() == null)
                user.setImg("img/default.jpg");
            //이미지를 선택하지 않았을 경우 이미지를 제외한 정보만 수정
            userService.save(u);
            return ResponseEntity.ok("UPDATE SUCCESS");
        }

    }


    @PostMapping(value = "/user")
    public ResponseEntity register(@ModelAttribute("user") @Valid User user, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (!result.hasErrors()) {
            if (!userService.isRegistable(user))
                return ResponseEntity.badRequest().body("Already Used Id");
            user.setRole(new Role("user"));
            log.info("Register start");
            try {
                String filePath = "img";
                String originalFileName = file.getOriginalFilename(); // 파일 이름
                String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf('.')); // 파일 확장자
                String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
                FileDB filedb = new FileDB(storedFileName, originalFileName, "image", filePath, null);
                registerServiceFacade.registerUser(file, filePath, filedb, user);
                log.info("Register Success " + user.getName());
            } catch (IOException ioe) {
                log.error(ioe.getMessage());
            } catch (StringIndexOutOfBoundsException e) {
                if (user.getImg() == null)
                    user.setImg("img/default.jpg");
                //이미지를 선택하지 않았을 경우 이미지를 제외한 정보만 수정
                userService.save(user);
                log.info("Register Success " + user.getName());
            }
        } // hasErrors end
        else {
            log.info("Register Error");
            return ResponseEntity.badRequest().body("Error! ");
        }
        return ResponseEntity.ok("success");
    }


}

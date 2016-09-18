package com.shape.web.controller;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.Role;
import com.shape.web.entity.User;
import com.shape.web.repository.FileDBRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.FileDBService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import lombok.extern.java.Log;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/**
 * Handles requests for the User .
 */
@Log
@Controller
public class UserController {

    @Autowired
    FileDBService fileDBService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value="/user/{projectIdx}",method = RequestMethod.GET)
    public List getUsers(@PathVariable("projectIdx") Integer projectIdx){
        return userService.getUsersByProject(projectService.getProject(projectIdx));
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String register(@ModelAttribute("tempUser") @Valid User tempUser, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (!result.hasErrors()) {
            User user = userService.getUserById(tempUser.getId());
            if (user == null)
                user = new User();
            user.setId(tempUser.getId());
            user.setName(tempUser.getName());
            if (!tempUser.getPw().equals("")) // 비밀번호란이 공란이 아닐경우
                user.setPw(tempUser.getPw());
            user.setRole(new Role("user"));
            log.info("Register start");
            try {
                String filePath = "img";
                String originalFileName = file.getOriginalFilename(); // 파일 이름
                String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
                String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
                FileDB filedb = new FileDB(storedFileName, originalFileName, filePath, "img", null);
                File folder = new File(filePath); // 폴더
                if (!folder.exists()) // 폴더 존재하지 않을 경우 만듬
                    folder.mkdirs();
                File transFile = new File(filePath + "/" + originalFileName); // 전송된 파일
                log.info("FILE NAME = " + file.getOriginalFilename());
                file.transferTo(transFile);
                fileDBService.save(filedb); // 파일 내용을 디비에 저장
                user.setImg("loadImg?name=" + storedFileName);

                filedb.setUser(user);
                userService.save(user);
                log.info("Register Success " + user.getName());
            } catch (IOException ioe) {

            } catch (StringIndexOutOfBoundsException e) {
                if (user.getImg() == null)
                    user.setImg("img/default.jpg");
                //이미지를 선택하지 않았을 경우 이미지를 제외한 정보만 수정
                userService.save(user);
                log.info("Register Success " + user.getName());
            } finally {
                return "login";
              }
        } // hasErrors end
        else {
            log.info("Register Error");
            return "login";
        }
    }


}

package com.shape.web.controller;

import com.shape.web.entity.User;
import com.shape.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by seongahjo on 2016. 2. 7..
 */
/**
 * Handles requests for the User .
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService us;

    @RequestMapping(value = "/loginok", method = RequestMethod.GET)    //"login.jsp"에서 넘어옴
    public String Loginok(@RequestParam(value = "userId") String userId, @RequestParam(value = "pw") String pw, HttpSession session) {
        User user = us.getById(userId);    //데이터베이스에서 아이디를 조회해서 유저데이터를 받아옴
        if (user != null) {
            logger.info("login : " + user.getId() + "/" + userId);
            if (user.getPw().equals(pw)) {
                session.setAttribute("userIdx", user.getUseridx());
                return "redirect:/dashboard/" + user.getId();    //url="/dashboard/{id}"로 유저아이디를 넘김
            }
        }
        return "redirect:/";
    }

    /*
    To register
    */

    /* @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam(value = "name") String name, @RequestParam(value = "userId") String userId, @RequestParam(value = "pw") String pw) {
        if (us.getById(userId) == null)
            us.add(userId, name, pw, "img/default.jpg");
        else
            return "redirect:/joinus";
        return "login";
    }*/

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        User tempUser = us.getById(user.getId());
        String filePath = "img";
        File folder = new File(filePath);
        if (!folder.exists())
            folder.mkdirs();

        File transFile = new File(filePath + "/" + file.getOriginalFilename());
        logger.info("FILE NAME = " + file.getOriginalFilename());
        try {
            file.transferTo(transFile);
        } catch (IOException ioe) {

        }
        if (tempUser != null) { // user가 존재하지 않음

        } else { //user가 존재
            user.setUseridx(tempUser.getUseridx());
            us.save(user);
        }
        return "redirect:/";
    }

}

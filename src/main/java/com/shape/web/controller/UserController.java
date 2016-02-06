package com.shape.web.controller;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.User;
import com.shape.web.service.FileDBService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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

    @Autowired
    FileDBService fs;

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
    @ResponseBody
    public void register(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        User tempUser = us.getById(user.getId());
        if (tempUser != null) // user가 존재함
            user.setUseridx(tempUser.getUseridx());
        try {
            String filePath = "img";
            String originalFileName = file.getOriginalFilename(); // 파일 이름
            String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
            String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
            FileDB filedb = new FileDB(storedFileName, originalFileName, filePath, "img", new Date());
            File folder = new File(filePath); // 폴더
            if (!folder.exists()) // 폴더 존재하지 않을 경우 만듬
                folder.mkdirs();
            File transFile = new File(filePath + "/" + storedFileName); // 전송된 파일
            logger.info("FILE NAME = " + file.getOriginalFilename());
            file.transferTo(transFile);
            fs.save(filedb); // 파일 내용을 디비에 저장
            user.setImg("loadImg?name=" + storedFileName);
            filedb.setUser(user);
            us.save(user);
        } catch (IOException ioe) {

        } catch (StringIndexOutOfBoundsException e) {
            //이미지를 선택하지 않았을 경우 이미지를 제외한 정보만 수정
            us.save(user);
        }
        }


}

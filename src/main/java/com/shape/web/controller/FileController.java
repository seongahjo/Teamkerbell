package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.FileDBService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by seongahjo on 2016. 1. 1..
 */
@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    AlarmService as;

    @Autowired
    UserService us;

    @Autowired
    ProjectService pjs;

    @Autowired
    FileDBService fs;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String Upload(@RequestParam(value = "idx") String projectIdx, @RequestParam(value = "userIdx") String userIdx, HttpServletRequest HSrequest, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) HSrequest;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        Project project = pjs.get(Integer.parseInt(projectIdx));
        User user = us.get(Integer.parseInt(userIdx));
        String filePath = FileUtil.getFoldername(Integer.parseInt(projectIdx), null); //프로젝트아이디, 날짜
        MultipartFile multipartFile = null;    //
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
        String type = null;
        Pattern pattern = Pattern.compile("\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());

            if (!multipartFile.isEmpty()) {
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                Matcher m = pattern.matcher(originalFileExtension);
                if (m.matches())
                    type = "img";
                else
                    type = "file";

                file = new File(filePath + "/" + originalFileName);
                multipartFile.transferTo(file);
                FileDB fd = new FileDB(storedFileName, originalFileName, filePath, type, new Date());
                fd.setUser(user);
                fd.setProject(project);
                fs.save(fd);

                for (User u : project.getUsers()) {
                    Alarm alarm = new Alarm(2, originalFileName, "download?date=" + new Date().getTime() + "&projectIdx=" + projectIdx + "&filename=" + storedFileName, new Date());
                    alarm.setUser(u);
                    alarm.setActor(user);
                    project.addAlarms(alarm);
                    as.save(alarm);
                }

                logger.info(filePath + "/" + originalFileName + " UPLOAD FINISHED!");
                return storedFileName;
            }
        }

        return "redirect:/chat?projectIdx=" + projectIdx;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void Download(@RequestParam(value = "filename", required = true) String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream in = null;
        OutputStream os = null;
        String client = "";
        FileDB fd=fs.getByStoredname(name);
        name=fd.getOriginalname();
        String folder=fd.getPath();
        File file = new File(folder + "/" + name);
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\"" + ";");
        if (client.contains("MSIE")) {
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("KSC5601"), "ISO8859_1"));
        } else {
            // IE 이외
            response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(name, "UTF-8") + "\"");
            response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");    //octet-stream->다운로드 창
        }    //response 헤더 설정해서

        response.setHeader("Content-Length", "" + file.length());
        in = new FileInputStream(file);
        os = response.getOutputStream();
        byte b[] = new byte[(int) file.length()];
        int leng = 0;
        logger.info("다운로드 " + name);
        while ((leng = in.read(b)) > 0) {
            os.write(b, 0, leng);
        }
        in.close();
        os.close();

    }
}

package com.shape.web.controller;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.parser.Tagging;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.repository.FileDBRepository;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * Created by seongahjo on 2016. 1. 1..
 * Handles requests for accessing file.
 */
@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileDBRepository fileDBRepository;
    @Autowired
    AlarmRepository alarmRepository;

    /*
    RESTFUL DOCUMENTATION
    FILE
       GET : DOWNLOAD (name)
       POST : UPLOAD ( idx, useridx, file)
     */
    /*
     To uploading file
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public Map Upload(@RequestParam(value = "idx") String projectIdx, HttpSession session, HttpServletRequest HSrequest) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) HSrequest;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        Project project = projectRepository.findOne(Integer.parseInt(projectIdx));
        User user = (User) session.getAttribute("user");
        String filePath = FileUtil.getFoldername(Integer.parseInt(projectIdx),null); //프로젝트아이디, 날짜
        MultipartFile multipartFile = null;    //
        HashMap<String, String> result = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
        String type = null;

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());

            if (!multipartFile.isEmpty()) {
                try {
                    result = new HashMap<>();
                    originalFileName = multipartFile.getOriginalFilename();
                    originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    originalFileName=originalFileName.substring(0,originalFileName.lastIndexOf("."));
                    storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                    if (FileUtil.IsImage(originalFileName))
                        type = "img";
                    else
                        type = "file";

                    file = new File(filePath + "/" + originalFileName+originalFileExtension);

                    multipartFile.transferTo(file);
                    String tag = Tagging.TagbyString(file);

                    FileDB fd = new FileDB(user, project, storedFileName, originalFileName, type, filePath, tag);
                    fileDBRepository.saveAndFlush(fd);

                    for (User u : project.getUsers()) {
                        Alarm alarm = new Alarm(2, originalFileName, "file?name=" + storedFileName, new Date(), project, u, user);
                        alarmRepository.saveAndFlush(alarm);
                    }

                    logger.info(filePath + "/" + originalFileName + " UPLOAD FINISHED!");
                    result.put("stored", storedFileName);
                    result.put("type", type);
                    result.put("original", originalFileName);
                    result.put("size", String.valueOf(file.length()));
                } catch (IOException e) {
                    // file io error
                } catch (NullPointerException e) {
                    logger.info("FILE UPLOAD NULL");
                }
            }
        }

        return result;
    }

    /*
        get files from corresponding project
     */
    @RequestMapping(value = "/file/{projectIdx}", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public String GetFilelist(@PathVariable("projectIdx") Integer projectIdx) {
        ArrayList<FileDB> filedb = (ArrayList) fileDBRepository.findByProjectOrderByCreatedatDesc(projectRepository.findOne(projectIdx));
        JsonObject jsonObject = new JsonObject();
        JsonArray array = new JsonArray();
        for (FileDB temp : filedb) {
            String type="<a href=../file?name="+temp.getStoredname()+">"+temp.getOriginalname()+"</a>";
            JsonArray arraytemp = new JsonArray();
            arraytemp.add(type);
            arraytemp.add(temp.getUser().getName());
            arraytemp.add(CommonUtils.DateTimeFormat(temp.getCreatedat()));
            arraytemp.add(temp.getTag());
            array.addArray(arraytemp);
        }
        jsonObject.putArray("data", array);

        return jsonObject.toString();
    }

    /*
    To download file
    */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void Download(@RequestParam(value = "name", required = true) String name, HttpServletResponse response) {
        try {
            InputStream in = null;
            OutputStream os = null;
            String client = "";
            FileDB fd = fileDBRepository.findByStoredname(name);
            name = fd.getOriginalname();
            String folder = fd.getPath();
            File file = new File(folder + "/" + name);
            response.reset();
            /*
            Header Setting
             */
            response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\"" + ";");
            if (client.contains("MSIE"))
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("KSC5601"), "ISO8859_1"));
            else {  // IE 이외
                response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(name, "UTF-8") + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");    //octet-stream->다운로드 창
            }    //response 헤더 설정해서

            response.setHeader("Content-Length", "" + file.length());

            /*
            Buffer 덮어쓰기
             */
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
        } catch (FileNotFoundException e) {
            // File 존재 안함
        } catch (IOException e) {
            //OutputStream Error
        }

    }
}

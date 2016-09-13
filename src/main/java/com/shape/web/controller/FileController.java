package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.parser.Tagging;
import com.shape.web.repository.AlarmRepository;
import com.shape.web.repository.FileDBRepository;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.AlarmService;
import com.shape.web.service.FileDBService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import java.util.stream.IntStream;

/**
 * Created by seongahjo on 2016. 1. 1..
 * Handles requests for accessing file.
 */
@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);



    @Autowired
    AlarmService alarmService;




    @Autowired
    ProjectService projectService;
    @Autowired
    FileDBService fileDBService;
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
        Project project = projectService.getProject(Integer.parseInt(projectIdx));
        User user = (User) session.getAttribute("user");
        String filePath = FileUtil.getFoldername(Integer.parseInt(projectIdx), null); //프로젝트아이디, 날짜
        MultipartFile multipartFile = null;    //
        HashMap<String, String> result = null;

        String type = null;

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

         while (iterator.hasNext()) {
        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        logger.info(projectIdx+" "+multipartFile.getName()+" "+project.getProjectidx()+" ");
        if (!multipartFile.isEmpty()) {
            try {
                result = new HashMap<>();
                String originalFileName = multipartFile.getOriginalFilename();
                String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                if (FileUtil.IsImage(originalFileName))
                    type = "img";
                else
                    type = "file";

                file = new File(filePath + "/" + storedFileName);

                multipartFile.transferTo(file);
                String tag = Tagging.TagbyString(file);

                FileDB fd = new FileDB(user, project, storedFileName, originalFileName, type, filePath, tag);
                fileDBService.save(fd);
                project.getUsers().forEach(u -> {
                    Alarm alarm = new Alarm(2, originalFileName, "file?name=" + storedFileName, new Date(), project, u, user);
                    alarmService.save(alarm);
                });


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
    To download file
    */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void Download(@RequestParam(value = "name", required = true) String name, HttpServletResponse response) {
        try {
            InputStream in = null;
            OutputStream os = null;
            String client = "";
            FileDB fd = fileDBService.getFileByStored(name);
            String originalName = fd.getOriginalname();
            String folder = fd.getPath();
            File file = new File(folder + "/" + name);
            response.reset();
            /*
            Header Setting
             */
            response.setHeader("Content-Disposition", "attachment;filename=\"" + originalName + "\"" + ";");
            if (client.contains("MSIE"))
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(originalName.getBytes("KSC5601"), "ISO8859_1"));
            else {  // IE 이외
                response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(originalName, "UTF-8") + "\"");
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

    /*
       get files from corresponding project
    */
    @RequestMapping(value = "/file/{projectIdx}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String GetFilelist(@PathVariable("projectIdx") Integer projectIdx) {
        List<Object[]> filedb = fileDBService.getFilesList(projectIdx);
        JsonObject jsonObject = new JsonObject();
        JsonArray array = new JsonArray();
        filedb.forEach(temp -> {
            String type2 = "<a href='#' onclick='openFile(\"" + temp[0] + "\",0)' data-toggle=\"modal\" data-target=\"#downloadModal\" >" + temp[0] + "</a>";
            JsonArray arraytemp = new JsonArray();
            arraytemp.add(type2);
            arraytemp.add(temp[1]);
            arraytemp.add(temp[2]);
            array.addArray(arraytemp);

        });
        jsonObject.putArray("data", array);

        return jsonObject.toString();
    }

    @RequestMapping(value = "/file/{projectIdx}/name", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List GetFileByName(@PathVariable("projectIdx") Integer projectIdx, @RequestParam("name") String name, @RequestParam("page") Integer page) {
        List<FileDB> fileDBs = fileDBService.getFilesByOriginal(projectService.getProject(projectIdx), name, page, 10);
        List<Map<String, String>> jsonar = new ArrayList();
        int count = 0 + 10 * page;
        IntStream.range(0, fileDBs.size()).forEach(idx -> {
                    FileDB temp = fileDBs.get(idx);
                    Map<String, String> json = new HashMap<>();
                    json.put("count", String.valueOf(count + idx+1));
                    json.put("file", "<a href=../file?name=" + temp.getStoredname() + ">" + "<i class= 'fa fa-file'>" + "file" + "</i></a>");
                    json.put("uploader", temp.getUser().getName());
                    json.put("date", CommonUtils.DateTimeFormat(temp.getCreatedat()));
                    jsonar.add(json);
                }
        );
      /*
        fileDBs.forEach(temp->{
            Map<String,String> json=new HashMap<>();
            json.put("count",String.valueOf(++count));
            json.put("file","<a href=../file?name="+temp.getStoredname()+">"+"<i class= 'fa fa-file'>"+"file"+"</i></a>");
            json.put("uploader",temp.getUser().getName());
            json.put("date",CommonUtils.DateTimeFormat(temp.getCreatedat()));
            jsonar.add(json);

        });*/

        return jsonar;
    }


}

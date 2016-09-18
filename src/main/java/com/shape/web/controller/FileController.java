package com.shape.web.controller;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.parser.Tagging;
import com.shape.web.service.FileDBService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.AlarmUtil;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
@Log
@RestController
public class FileController {


    @Autowired
    ProjectService projectService;

    @Autowired
    FileDBService fileDBService;

    @Autowired
    UserService userService;

    /*
    RESTFUL DOCUMENTATION
    FILE
       GET : DOWNLOAD (name)
       POST : UPLOAD ( idx, useridx, file)
     */




    @RequestMapping(value="/file/{projectIdx}/img", method = RequestMethod.GET)
    public List getimgs(@PathVariable("projectIdx")Integer projectIdx){
        return fileDBService.getImgs(projectService.getProject(projectIdx));
    }

    /*
     To uploading file
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public Map Upload(@RequestParam(value = "idx") String projectIdx, HttpSession session, HttpServletRequest HSrequest) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) HSrequest;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        Project project = projectService.getProject(Integer.parseInt(projectIdx));
        User user = (User) session.getAttribute("user");
        String filePath = FileUtil.getFoldername(Integer.parseInt(projectIdx), null); //프로젝트아이디, 날짜
        MultipartFile multipartFile = null;
        HashMap<String, String> result = null;

        String type = null;

        File file = new File(filePath); // 폴더

        if (!file.exists())
            file.mkdirs(); // 폴더 존재 안할시 생성

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if (!multipartFile.isEmpty()) {
                try {
                    result = new HashMap<>();
                    String originalFileName = multipartFile.getOriginalFilename(); // 파일 이름
                    String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자
                    String storedFileName = CommonUtils.getRandomString() + originalFileExtension; // 암호화된 파일 이름
                    type = FileUtil.getFileType(originalFileName); // 파일 타입 확인
                    file = new File(filePath + "/" + storedFileName); // 파일 해당 디렉토리 생성
                    multipartFile.transferTo(file); // 파일 저장
                    String tag = Tagging.TagbyString(file); //태그 생성
                    FileDB fd = new FileDB(user, project, storedFileName, originalFileName, type, filePath, tag); // 파일 객체 생성
                    fileDBService.save(fd); //디비에 저장
                    List lu = userService.getUsersByProject(project); // 프로젝트에 해당하는 유저 리스트 반환
                    Alarm alarm = new Alarm(2, originalFileName, "file?name=" + storedFileName, new Date(), project, user); // 알람 객체 생성
                    AlarmUtil.postAlarm(lu, alarm, false); // 알람 보내기
                    log.info(filePath + "/" + originalFileName + " UPLOAD FINISHED!");
                    result.put("stored", storedFileName);
                    result.put("type", type);
                    result.put("original", originalFileName);
                    result.put("size", String.valueOf(file.length()));
                } catch (IOException e) {
                    // file io error
                } catch (NullPointerException e) {
                    log.info("FILE UPLOAD NULL");
                }
            }
        }

        return result;
    }

    /*
    To download file
    */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void Download(@RequestParam(value = "name", required = true) String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream in = null;
            OutputStream os = null;
            FileDB fd = fileDBService.getFileByStored(name);
            String originalName = fd.getOriginalname();
            String folder = fd.getPath();
            File file = new File(folder + "/" + name);
            response.reset();
            FileUtil.setDownloadHeader(originalName, file, request, response);
            in = new BufferedInputStream(new FileInputStream(file));
            os = new BufferedOutputStream(response.getOutputStream());
            byte b[] = new byte[(int) file.length()];
            int leng = 0;
            log.info("다운로드 " + name);
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
    public List GetFileByName(@PathVariable("projectIdx") Integer projectIdx, @RequestParam("name") String name, @RequestParam("page") Integer page) {
        List<FileDB> fileDBs = fileDBService.getFilesByOriginal(projectService.getProject(projectIdx), name, page, 10);
        List<Map<String, String>> jsonar = new ArrayList();
        int count = 0 + 10 * page;
        IntStream.range(0, fileDBs.size()).forEach(idx -> {
                    FileDB temp = fileDBs.get(idx);
                    Map<String, String> json = new HashMap<>();
                    json.put("count", String.valueOf(count + idx + 1));
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

package com.shape.web.util;

import com.shape.web.entity.FileDB;
import com.shape.web.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    // Static 메소드로만 만들기
    @Autowired
    static FileDBRepository fileDBRepository;
/*
    public static String getFoldername(Integer projectIdx){
        return RepositoryUtil.repositoryPrefix+"."+projectIdx;
    }*/

    public static String getFoldername(final int project_id, final Date date) {
        Date dates = null;

        if (date == null)
            dates = new Date();
        else
            dates = date;

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String filename = "team/" + format.format(dates);
        filename += "_" + project_id;

        return filename;
    }

    public static void MakeMinute(final int project_id, final String memo) throws Exception {
        String filename = getFoldername(project_id, null);
        FileWriter fw = new FileWriter(filename + "/minute.txt");
        fw.write(memo);
        fw.close();
    }

    public static String DecodeFile(String storedFileName) {
        FileDB fd = fileDBRepository.findByStoredname(storedFileName);
        return fd.getOriginalname();
    }

    public static boolean IsImage(final String filename) {
        String FileExtension = filename.substring(filename.lastIndexOf("."));

        Pattern pattern = Pattern.compile("\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(FileExtension);
        return m.matches();
    }

    public static String getFileType(String filename) {
        if (IsImage(filename))
            return "image";
        else
            return "file";

    }

    public static void setDownloadHeader(final String  filename, final File file, final HttpServletRequest request, HttpServletResponse response) {
        String userAgent = request.getHeader("User-Agent");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"" + ";");
            if (userAgent.contains("MSIE"))
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("KSC5601"), "ISO8859_1"));
            else {  // IE 이외
                response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");    //octet-stream->다운로드 창
            }    //response 헤더 설정해서
            response.setHeader("Content-Length", "" + file.length());

        } catch (IOException e) {

        }
    }
}


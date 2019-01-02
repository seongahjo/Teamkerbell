package com.shape.web.util;

import com.shape.web.entity.FileDB;
import com.shape.web.repository.FileDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Component
public class FileUtil {
    private FileDBRepository fileDBRepository;


    @Autowired
    public FileUtil(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    private enum Header {

        DISPOSITION("Content-disposition"),
        TYPE("Content-Type"),
        LENGTH("Content-Length");

        Header(String headerName) {
            this.headerName = headerName;
        }

        private String headerName;

    }

    public static String getFoldername(final int project_id, final Date date) {
        Date dates = date == null ? new Date() : date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String filename = "team/" + format.format(dates);
        filename += "_" + project_id;
        return filename;
    }

    public static void makeMinute(final int project_id, final String memo) {
        String filename = getFoldername(project_id, null);
        try (FileWriter fw = new FileWriter(filename + "/minute.txt")) {
            fw.write(memo);
        } catch (IOException e) {
            log.error("Open File Error ", e);
        }
    }

    public String decodeFile(String storedFileName) {
        FileDB fd = fileDBRepository.findByStoredname(storedFileName);
        return fd.getOriginalname();
    }

    private static boolean isImage(final String filename) {
        String fileExtension = filename.substring(filename.lastIndexOf('.'));
        Pattern pattern = Pattern.compile("\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(fileExtension);
        return m.matches();
    }

    public static String getFileType(String filename) {
        if (isImage(filename))
            return "image";
        else
            return "file";

    }

    public static void setDownloadHeader(final String filename, final File file, final HttpServletRequest request, HttpServletResponse response) {
        String userAgent = request.getHeader("User-Agent");
        try {
            response.setHeader(Header.DISPOSITION.name(), "attachment;filename=\"" + filename + "\"" + ";");
            if (userAgent.contains("MSIE"))
                response.setHeader(Header.DISPOSITION.name(), "attachment; filename=" + new String(filename.getBytes("KSC5601"), "ISO8859_1"));
            else {  // IE 이외
                response.setHeader(Header.DISPOSITION.name(), "attachment; filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"");
                response.setHeader(Header.TYPE.name(), "application/octet-stream; charset=utf-8");    //octet-stream->다운로드 창
            }    //response 헤더 설정해서
            response.setHeader(Header.LENGTH.name(), "" + file.length());

        } catch (IOException e) {
            log.error("Error ", e);
        }
    }
}


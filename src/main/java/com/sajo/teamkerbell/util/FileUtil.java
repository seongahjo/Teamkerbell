package com.sajo.teamkerbell.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class FileUtil {

    private enum Header {

        DISPOSITION("Content-disposition"),
        TYPE("Content-Type"),
        LENGTH("Content-Length");

        Header(String headerName) {
            this.headerName = headerName;
        }

        public String getName() {
            return headerName;
        }

        private String headerName;

    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    public static String getFoldername(final int projectId, final LocalDate date) {
        LocalDate dates = date == null ? LocalDate.now() : date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder sb = new StringBuilder();
        sb.append("team/");
        sb.append(format.format(dates));
        sb.append("_");
        sb.append(projectId);
        return sb.toString();
    }

    public static void makeMinute(final int projectId, final String memo) {
        String filename = getFoldername(projectId, null);
        try (FileWriter fw = new FileWriter(filename + "/minute.txt")) {
            fw.write(memo);
        } catch (IOException e) {
            log.error("Open File Error ", e);
        }
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
            response.setHeader(Header.DISPOSITION.getName(), "attachment;filename=\"" + filename + "\"" + ";");
            if (userAgent.contains("MSIE"))
                response.setHeader(Header.DISPOSITION.getName(), "attachment; filename=" + new String(filename.getBytes("KSC5601"), "ISO8859_1"));
            else {  // IE 이외
                response.setHeader(Header.DISPOSITION.getName(), "attachment; filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"");
                response.setHeader(Header.TYPE.getName(), "application/octet-stream; charset=utf-8");    //octet-stream->다운로드 창
            }    //response 헤더 설정해서
            response.setHeader(Header.LENGTH.getName(), "" + file.length());

        } catch (IOException e) {
            log.error("Error ", e);
        }
    }
}


package com.shape.web.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shape.web.entity.FileDB;
import com.shape.web.service.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
    // Static 메소드로만 만들기
    @Autowired
    static FileDBService fs;

    public static String getFoldername(int project_id, Date date) {
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

    public static void MakeMinute(int project_id, String memo) throws Exception {
        String filename = FileUtil.getFoldername(project_id, null);
        FileWriter fw = new FileWriter(filename + "/minute.txt");
        fw.write(memo);
        fw.close();
    }
    public static String DecodeFile(String storedFileName){
       FileDB fd=fs.getByStoredname(storedFileName);
        return fd.getOriginalname();
    }
}


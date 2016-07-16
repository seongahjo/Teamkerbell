package com.shape.web.util;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.shape.web.entity.FileDB;
import com.shape.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class FileUtil {
    // Static 메소드로만 만들기
   @Autowired
   static FileDBRepository fileDBRepository;

    public static String getFoldername(Integer projectIdx){
        return RepositoryUtil.repositoryPrefix+"."+projectIdx;
    }
/*
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
    }*/

    public static void MakeMinute(int project_id, String memo) throws Exception {
        String filename = getFoldername(project_id);
        FileWriter fw = new FileWriter(filename + "/minute.txt");
        fw.write(memo);
        fw.close();
    }
    public static String DecodeFile(String storedFileName){
       FileDB fd=fileDBRepository.findByStoredname(storedFileName);
        return fd.getOriginalname();
    }
    public static boolean IsImage(String filename){
        String FileExtension = filename.substring(filename.lastIndexOf("."));

        Pattern pattern = Pattern.compile("\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(FileExtension);
        return m.matches();
    }
}


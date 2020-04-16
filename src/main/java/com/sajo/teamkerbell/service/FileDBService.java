package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.FileDB;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface FileDBService {
    FileDB upload(MultipartFile file, String filePath);

    FileDB download(String fileName, HttpServletRequest request, HttpServletResponse response);

    FileDB render(String name, HttpServletResponse response);

    FileDB save(FileDB f);

}

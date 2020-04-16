package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.FileDB;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface FileDBService {
    FileDB upload(MultipartFile file, String filePath);

    FileDB save(FileDB f);

}

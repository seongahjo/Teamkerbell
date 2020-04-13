package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface FileDBService {
    FileDB getFile(Integer f);

    List<FileDB> getFilesList(Integer projectIdx);

    List<FileDB> getFilesByOriginal(Project p, String o, Integer page, Integer count);

    FileDB getFileByStored(String s);

    List<FileDB> getImgs(Project p);

    FileDB save(FileDB f);

}

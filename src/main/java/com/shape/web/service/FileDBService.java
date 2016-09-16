package com.shape.web.service;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface FileDBService {
    FileDB getFile(Integer f);
    List getFilesList(Integer projectIdx);
    //List getFilesByProject(Project p);
    List getFilesByOriginal(Project p,String o,Integer page,Integer count);
    FileDB getFileByStored(String s);
    List getImgs(Project p);
    FileDB save(FileDB f);

}

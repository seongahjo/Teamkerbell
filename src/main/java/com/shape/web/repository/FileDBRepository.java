package com.shape.web.repository;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface FileDBRepository extends JpaRepository<FileDB,Integer> {
    List<FileDB> findByProjectOrderByDateDesc(Project project);
    FileDB findByStoredname(String storedname);
    FileDB findByOriginalname(String originalname);
    List<FileDB> findByProjectAndTypeOrderByDateDesc(Project project,String type);

}

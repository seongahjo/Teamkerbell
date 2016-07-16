package com.shape.web.repository;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface FileDBRepository extends JpaRepository<FileDB,Integer> {
    //@Cacheable("files")
    List<FileDB> findByProjectOrderByCreatedatDesc(Project project);
    //@Cacheable("files-store")
    FileDB findByStoredname(String storedname);
    //@Cacheable("files-original")
    FileDB findByOriginalname(String originalname);
    //@Cacheable("imgs")
    List<FileDB> findByProjectAndTypeOrderByCreatedatDesc(Project project,String type);

}

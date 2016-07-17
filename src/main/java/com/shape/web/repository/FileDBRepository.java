package com.shape.web.repository;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface FileDBRepository extends JpaRepository<FileDB,Integer> {
    List<Object[]> groupbytest(Integer projectidx);
    //@Cacheable("files")
    List<FileDB> findByProjectOrderByCreatedatDesc(Project project);
    //@Cacheable("files-store")
    FileDB findByStoredname(String storedname);
    //@Cacheable("files-original")
    List<FileDB> findByProjectAndOriginalnameOrderByCreatedatDesc(Project project,String originalname,Pageable pageable);
    //@Cacheable("imgs")
    List<FileDB> findByProjectAndTypeOrderByCreatedatDesc(Project project,String type);

}

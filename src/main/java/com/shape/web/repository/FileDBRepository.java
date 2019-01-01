package com.shape.web.repository;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
@Repository
public interface FileDBRepository extends JpaRepository<FileDB,Integer> {
    List<Object[]> groupbytest(Integer projectidx);
    //List<FileDB> findByProjectOrderByCreatedatDesc(Project project);
    FileDB findByStoredname(String storedname);
    List<FileDB> findByProjectAndOriginalnameOrderByCreatedatDesc(Project project,String originalname,Pageable pageable);
    List<FileDB> findByProjectAndTypeOrderByCreatedatDesc(Project project,String type);

}

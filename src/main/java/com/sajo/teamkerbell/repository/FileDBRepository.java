package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.FileDB;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Integer> {
    FileDB findByStoredName(String storedName);

    List<FileDB> findByProjectId(Integer projectId, Pageable pageable);

    List<FileDB> findByProjectIdAndFileType(Integer projectId, FileDB.FileType fileType, Pageable pageable);
}

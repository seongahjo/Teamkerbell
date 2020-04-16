package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Integer> {
    FileDB findByStoredName(String storedName);
}

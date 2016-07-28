package com.shape.web.repository;

import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface MinuteRepository extends JpaRepository<Minute,Integer> {
    List<Minute> findByProject(Project project);
    Minute findByProjectAndDate(Project project,Date date);

}

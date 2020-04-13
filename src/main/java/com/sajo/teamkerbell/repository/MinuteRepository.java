package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface MinuteRepository extends JpaRepository<Minute,Integer> {
    List<Minute> findByProject(Project project);
    Minute findByProjectAndDate(Project project,Date date);

}

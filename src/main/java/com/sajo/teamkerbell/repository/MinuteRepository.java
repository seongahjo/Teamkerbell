package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Minute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface MinuteRepository extends JpaRepository<Minute, Integer> {
    List<Minute> findByProjectId(Integer projectId);

    Minute findByProjectIdAndDate(Integer projectId, LocalDate date);

}

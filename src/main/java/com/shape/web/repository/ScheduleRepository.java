package com.shape.web.repository;

import com.shape.web.entity.Project;
import com.shape.web.entity.Schedule;
import com.shape.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    List<Schedule> findByProject_Users( User user);
}

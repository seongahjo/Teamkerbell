package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    List<Schedule> findByProject_Users( User user);
}

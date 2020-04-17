package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s FROM Schedule s left join Project p on s.projectId = p.projectId left join p.users u where u.userId = ?1 ")
    List<Schedule> findByUserId(Integer userId, Pageable pageable);
}

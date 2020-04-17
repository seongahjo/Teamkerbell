package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Timeline;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    @Query("select t from Timeline t left join Project p on p.projectId = t.projectId left join p.users u where u.userId = ?1")
    List<Timeline> findByUserId(Integer userId, Pageable pageable);
}

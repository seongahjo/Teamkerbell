package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("select p from Project p left join p.users u where u.userId = ?1")
    List<Project> findByUserId(Integer userId, Pageable pageable);
}

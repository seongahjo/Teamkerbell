package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    List<Project> findByUsers(User user);
    List<Object> todolistPercentage(Integer projectidx);
    List<Project> findByUsers(User user,Pageable pageable);

}

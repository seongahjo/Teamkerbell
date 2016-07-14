package com.shape.web.repository;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    List<Project> findByUsers(User user);

}

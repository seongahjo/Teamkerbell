package com.shape.web.repository;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(String id);

    List<User> findByProjects(Project project);
}

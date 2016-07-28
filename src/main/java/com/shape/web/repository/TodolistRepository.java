package com.shape.web.repository;

import com.shape.web.entity.Project;
import com.shape.web.entity.Todolist;
import com.shape.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface TodolistRepository extends JpaRepository<Todolist,Integer> {
    List<Todolist> findByUser(User user);
    List<Todolist> findByProject(Project project);
}

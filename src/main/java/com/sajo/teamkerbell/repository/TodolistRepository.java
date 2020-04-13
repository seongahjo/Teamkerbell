package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.Todolist;
import com.sajo.teamkerbell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface TodolistRepository extends JpaRepository<Todolist,Integer> {
    List<Todolist> findDistinctByUserAndProject_Processed(User user,boolean isprocessed);
    List<Todolist> findByProject(Project project);
}

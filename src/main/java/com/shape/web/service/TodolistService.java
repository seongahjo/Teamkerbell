package com.shape.web.service;

import com.shape.web.entity.Project;
import com.shape.web.entity.Todolist;
import com.shape.web.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface TodolistService {
    List<Todolist> getTodolists(User u);

    List<Todolist> getTodolists(Project p);

    Todolist getTodolist(Integer idx);

    Todolist save(Todolist t);
}

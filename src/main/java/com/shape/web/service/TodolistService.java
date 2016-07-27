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
    List getTodolists(User u);
    List getTodolists(Project p);
    Todolist save(Todolist t);
}

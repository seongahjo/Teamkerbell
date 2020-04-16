package com.sajo.teamkerbell.service.impl;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.Todolist;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.TodolistRepository;
import com.sajo.teamkerbell.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
@Transactional
public class TodolistServiceImpl implements TodolistService {

    private TodolistRepository todolistRepository;

    @Autowired
    public TodolistServiceImpl(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    @Override
    public List<Todolist> getTodolists(User u) {
        return todolistRepository.findByUserId(u.getUserId()).stream().filter(t -> !t.isFinished()).collect(Collectors.toList());
    }

    @Override
    public List<Todolist> getTodolists(Project p) {
        return todolistRepository.findByProjectId(p.getProjectId());
    }

    @Override
    public Todolist getTodolist(Integer idx) {
        return todolistRepository.findOne(idx);
    }

    @Override
    public Todolist save(Todolist t) {
        return todolistRepository.save(t);
    }
}

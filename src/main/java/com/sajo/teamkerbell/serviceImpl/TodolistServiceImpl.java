package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.Todolist;
import com.sajo.teamkerbell.repository.TodolistRepository;
import com.sajo.teamkerbell.service.TodolistService;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public class TodolistServiceImpl implements TodolistService {

    private TodolistRepository todolistRepository;

    @Autowired
    public TodolistServiceImpl(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    @Override
    public List<Todolist> getTodolists(User u) {
        return todolistRepository.findDistinctByUserAndProject_Processed(u, true);
    }

    @Override
    @Cacheable(value = "todolists", key = "'project:'.concat(#p0.projectidx).concat(':todolists')")
    public List<Todolist> getTodolists(Project p) {
        return todolistRepository.findByProject(p);
    }

    @Override
    @Cacheable(value = "todolist", key = "'todolist:'.concat(#p0)")
    public Todolist getTodolist(Integer idx) {
        return todolistRepository.findOne(idx);
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "todolist", key = "'todolist:'.concat(#p0.todolistidx)"),
            @CacheEvict(value = "todolists", key = "'project:'.concat(#p0.project.projectidx).concat(':todolists')"),
            @CacheEvict(value = "todolists", key = "'user:'.concat(#p0.user.useridx).concat(':todolists')")
    })
    public Todolist save(Todolist t) {
        return todolistRepository.saveAndFlush(t);
    }
}

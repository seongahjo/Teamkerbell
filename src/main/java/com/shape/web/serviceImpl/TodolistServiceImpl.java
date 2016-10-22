package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.Todolist;
import com.shape.web.entity.User;
import com.shape.web.repository.TodolistRepository;
import com.shape.web.service.TodolistService;
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
    /*
     todolist:'todolistidx'
     user:'useridx':todolists
     project:'projectidx':todolists
     */
    @Autowired
    TodolistRepository todolistRepository;

    @Override
    @Cacheable(value = "todolists", key = "'user:'.concat(#p0.useridx).concat(':todolists')")
    public List getTodolists(User u) {
        return todolistRepository.findDistinctByUserAndProject_Processed(u,true);
    }

    @Override
    @Cacheable(value = "todolists", key = "'project:'.concat(#p0.projectidx).concat(':todolists')")
    public List getTodolists(Project p) {
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

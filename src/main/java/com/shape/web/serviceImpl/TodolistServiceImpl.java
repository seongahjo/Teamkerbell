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
    @Autowired
    TodolistRepository todolistRepository;

    @Override
    @Cacheable(value = "todolists", key = "'user:'.concat(#p0.id).concat(':todolists')")
    public List getTodolists(User u) {
        return todolistRepository.findByUser(u);
    }
    @Override
    @Cacheable(value = "todolists", key = "'project:'.concat(#p0.projectidx).concat(':todolists')")
    public List getTodolists(Project p){
        return todolistRepository.findByProject(p);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "todolists", key = "'project:'.concat(#p0.project.projectidx).concat(':todolists')"),
            @CacheEvict(value = "todolists", key = "'user:'.concat(#p0.user.id).concat(':todolists')")
    })
    public Todolist save(Todolist t) {
        return todolistRepository.saveAndFlush(t);
    }
}

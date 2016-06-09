package com.shape.web.service;

import com.shape.web.entity.Todolist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TodolistService {
    @Resource
    private SessionFactory sessionFactory;

    public Todolist get(Integer id) {
        Session session=sessionFactory.openSession();
        Todolist todolist= (Todolist)session.get(Todolist.class,id);
        session.close();
        return todolist;
    }

    public Todolist save(Todolist Todolist) {
        Session session=sessionFactory.openSession();
        session.saveOrUpdate(Todolist);
        session.flush();
        session.close();
        return Todolist;
    }

    public void delete(Integer id) {
        Todolist Todolist = get(id);
        Session session = sessionFactory.openSession();
        session.delete(Todolist);
        session.flush();
        session.close();
    }

}

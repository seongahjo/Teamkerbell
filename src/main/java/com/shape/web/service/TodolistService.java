package com.shape.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.shape.web.entity.Todolist;

@Service
public class TodolistService {
    @Resource
    private SessionFactory sessionFactory;

    public Todolist get(Integer id) {
        Session session=sessionFactory.openSession();
        Todolist todolist= (Todolist)session.get(Todolist.class,id);
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

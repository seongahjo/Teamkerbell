package com.shape.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.shape.web.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private SessionFactory sessionFactory;

    public User save(User user){
        Session session=sessionFactory.openSession();
        session.merge(user);
        session.flush();
        session.close();
        return user;
    }

    public List<Todolist> getTodolist(User user){
        Session session=sessionFactory.openSession();
        List<Todolist> lt = session.createCriteria(Todolist.class)
                .createAlias("user","user")
                .add(Restrictions.eq("user.useridx",user.getUseridx()))
                .addOrder(Order.desc("todolistidx"))
                .list();
        session.close();
        return lt;
    }
    public User getById(String id) {
        Session session = sessionFactory.openSession();
        User u=(User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        return u;
    }

    public User get(Integer useridx) {
        Session session = sessionFactory.openSession();
        User u=(User) session.get(User.class, useridx);
        session.close();
        return u;

    }
    public void addProject(Integer userIdx,Project project){
        Session session=sessionFactory.openSession();
        User user=(User)session.get(User.class,userIdx);
        user.addProject(project);
        session.update(user);
        session.flush();
        session.close();
    }

    public void deleteProject(Integer projectIdx) {
        Session session = sessionFactory.openSession();
        Project project=(Project)session.get(Project.class,projectIdx);
        for(User u :project.getUsers()){
            u.getProjects().remove(project);
            session.merge(u);
        }
        session.delete(project);
        session.flush();
        session.close();
    }

    public User add(String id, String name,String pw, String img) {
        Session session = sessionFactory.openSession();
         User   user=new User();
            user.setId(id);
            user.setName(name);
            user.setImg(img);
            user.setPw(pw);
        session.save(user);
        session.flush();
        session.close();
        return user;
    }
    public User edit(Integer useridx, String id, String pw, String name, String img) {
        User user = get(useridx);
        Session session = sessionFactory.openSession();
        user.setName(name);
        user.setImg(img);
        user.setPw(pw);
        session.update(user);
        session.flush();
        session.close();
        return user;
    }

    public List<Schedule> getScheudles(User user){
        Session session=sessionFactory.openSession();
        List<Schedule> ls = session.createCriteria(Schedule.class)
            .createAlias("project","project")
                .createAlias("project.users","users")
                .add(Restrictions.eq("users.useridx",user.getUseridx()))
                .addOrder(Order.desc("scheduleidx"))
                .list();
        session.close();
        return ls;
    }

    public List<Project> getProjects(User user){
        Session session=sessionFactory.openSession();
        List<Project> lp=session.createCriteria(Project.class)
                .createAlias("users","users")
                .add(Restrictions.eq("users.useridx",user.getUseridx()))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("projectidx"))
                .list();
        session.close();
        return lp;
    }
    public List<Alarm> getAlarms(Integer userIdx){
        Session session=sessionFactory.openSession();
        List<Alarm> la=session.createCriteria(Alarm.class)
                .createAlias("user","user")
                .add(Restrictions.eq("user.useridx",userIdx))
                .add(Restrictions.eq("isshow",true))
                .add(Restrictions.eq("contentid",0))
                .addOrder(Order.asc("date"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return la;
    }

    public List<Alarm> getTimeline(User user){
        Session session=sessionFactory.openSession();
        List<Alarm> la=session.createCriteria(Alarm.class)
                .createAlias("user","user")
                .add(Restrictions.eq("user.useridx",user.getUseridx()))
                .add(Restrictions.eq("isshow",true))
                .add(Restrictions.ne("contentid",0))
                .addOrder(Order.desc("date"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return la;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        List<User> users = (List<User>) session.createCriteria(User.class).list();
        session.close();
        return users;
    }

    public void delete(Integer useridx) {
        User user = get(useridx);
        Session session = sessionFactory.openSession();
        session.delete(user);
        session.flush();
        session.close();
    }

}

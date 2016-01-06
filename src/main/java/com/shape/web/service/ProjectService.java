package com.shape.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.shape.web.entity.*;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Resource
    private SessionFactory sessionFactory;

    public Project add(String name, int leaderidx, String minutes) {
        Project project = new Project(name, leaderidx, minutes);
        Session session = sessionFactory.openSession();
        session.save(project);
        session.flush();
        session.close();
        return project;
    }

    public Project get(Integer id) {
        Session session = sessionFactory.openSession();
        Project pj = (Project) session.get(Project.class, id);
        session.close();
        return pj;
    }

    public Project save(Project project) {
        Session session = sessionFactory.openSession();
        session.merge(project);
        session.flush();
        session.close();
        return project;
    }

    public List<Minute> getMinutes(Project project) {
        Session session = sessionFactory.openSession();
        List<Minute> lm = session.createCriteria(Minute.class)
                .createAlias("project", "project")
                .add(Restrictions.eq("project.projectidx", project.getProjectidx()))
                .addOrder(Order.desc("date"))
                .list();
        session.close();
        return lm;
    }

    public List<Schedule> getSchedules(Integer projectIdx) {
        Session session = sessionFactory.openSession();
       Project project=(Project)session.get(Project.class,projectIdx);
        List<Schedule> ls= new ArrayList<Schedule>();


            for(Schedule s : project.getSchedules()){
                ls.add(s);
            }

/*
        List<Schedule> ls = session.createCriteria(Schedule.class)
                .createAlias("project", "project")
                .add(Restrictions.eq("project.projectidx", project.getProjectidx()))
                .createAlias("appointments", "appointments", JoinType.LEFT_OUTER_JOIN)
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("scheduleidx"))
                        .add(Projections.property("content").as("content"))
                        .add(Projections.property("state").as("state"))
                        .add(Projections.property("startdate").as("startdate"))
                )
                .addOrder(Order.desc("scheduleidx"))
                .list();*/
        session.close();
        return ls;
    }

    public List<FileDB> getImgs(Project project) {
        Session session = sessionFactory.openSession();
        List<FileDB> lfd = session.createCriteria(FileDB.class)
                .createAlias("project", "project")
                .add(Restrictions.eq("project.projectidx", project.getProjectidx()))
                .add(Restrictions.eq("type", "img"))
                .addOrder(Order.desc("date"))
                .list();
        session.close();
        return lfd;
    }

    public List<User> getUsers(Project project) {
        Session session = sessionFactory.openSession();
        List<User> lu = session.createCriteria(User.class)
                .createAlias("projects","projects")
                .add(Restrictions.eq("projects.projectidx",project.getProjectidx()))
                .addOrder(Order.asc("useridx"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return lu;
    }

    @SuppressWarnings("unchecked")
    public List<Project> getAll() {
        Session session = sessionFactory.openSession();
        List<Project> lp = (List<Project>) session.createCriteria(Project.class).list();
        session.close();
        return lp;
    }

    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Project project = (Project)session.get(Project.class,id);
        session.delete(project);
        session.flush();
        session.close();
    }
}

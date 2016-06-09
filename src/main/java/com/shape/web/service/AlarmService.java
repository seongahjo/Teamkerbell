package com.shape.web.service;

import com.shape.web.entity.Alarm;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AlarmService {
    @Resource
    private SessionFactory sessionFactory;


    public void Accept(Alarm alarm){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("DELETE from Alarm where ");
        query.executeUpdate();
        session.flush();
        session.close();
    }

    public Alarm get(Integer id) {
        Session session=sessionFactory.openSession();
        Alarm alarm=(Alarm)session.get(Alarm.class,id);
        session.close();
        return alarm;
    }
    public Alarm save(Alarm Alarm) {
        Session session=sessionFactory.openSession();
        session.saveOrUpdate(Alarm);
        session.flush();
        session.close();
        return Alarm;
    }

    public void delete(Integer id) {
        Alarm Alarm = get(id);
        Session session = sessionFactory.openSession();
        session.delete(Alarm);
        session.flush();
        session.close();
    }

}

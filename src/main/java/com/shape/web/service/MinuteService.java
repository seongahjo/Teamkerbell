package com.shape.web.service;

import com.shape.web.entity.Minute;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;



@Service
public class MinuteService {
    @Resource
    private SessionFactory sessionFactory;
    public Minute getByDate(Date date){
        Session session=sessionFactory.openSession();
       Minute minute= (Minute)session.createCriteria(Minute.class).add(Restrictions.eq("date",date)).uniqueResult();
        session.close();
        return minute;
    }
    public Minute get(Integer id) {
        Session session=sessionFactory.openSession();
        Minute minute=(Minute)session.get(Minute.class,id);
        return minute;
    }

    public Minute save(Minute Minute) {
        Session session=sessionFactory.openSession();
        session.saveOrUpdate(Minute);
        session.flush();
        session.close();
        return Minute;
    }

    public void delete(Integer id) {
        Minute Minute = get(id);
        Session session = sessionFactory.openSession();
        session.delete(Minute);
        session.flush();
        session.close();
    }

}

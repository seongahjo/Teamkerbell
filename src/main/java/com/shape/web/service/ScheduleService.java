package com.shape.web.service;

import com.shape.web.entity.Appointment;
import com.shape.web.entity.Schedule;
import com.shape.web.entity.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleService {

    @Resource
    private SessionFactory sessionFactory;


    public Schedule get(Integer id) {
        Session session = sessionFactory.openSession();
        Schedule schedule = (Schedule) session.get(Schedule.class, id);
        session.close();
        return schedule;
    }

    public Schedule save(Schedule Schedule) {
        Session session = sessionFactory.openSession();
        session.merge(Schedule);
        session.flush();
        session.close();
        return Schedule;

    }

    public void delete(Integer id) {
        Schedule Schedule = get(id);
        Session session = sessionFactory.openSession();
        session.delete(Schedule);
        session.flush();
        session.close();
    }

    public void updateAm(Integer id, Integer state, Date date) {
        Session session = sessionFactory.openSession();
        Schedule schedule = (Schedule) session.get(Schedule.class, id);
        Set<Appointment> lap=schedule.getAppointments();
        if(lap!=null) {
            for (Appointment ap : lap) {
                if (ap.getDate().equals(date)) {
                    ap.setState(state);
                    session.merge(ap);
                }
            }
            session.close();
        }
    }

    public List<Appointment> getAppointments(Schedule schedule) {
        Session session = sessionFactory.openSession();
        List<Appointment> la = session.createCriteria(Appointment.class)
                .createAlias("schedule", "schedule")
                .add(Restrictions.eq("schedule.scheduleidx", schedule.getScheduleidx()))
                .add(Restrictions.eq("state",1))
                .addOrder(Order.asc("appointmentidx"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return la;

    }
}


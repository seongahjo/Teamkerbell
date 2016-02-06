package com.shape.web.service;

import com.shape.web.entity.Appointment;
import com.shape.web.entity.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Resource
    private SessionFactory sessionFactory;
    @Autowired
    ScheduleService ss;

    public Appointment get(Integer id) {
        Session session = sessionFactory.openSession();
        return (Appointment) session.get(Appointment.class, id);
    }

    public Appointment save(Appointment Appointment) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(Appointment);
        session.flush();
        session.close();
        return Appointment;
    }

    public Appointment make(Appointment appointment, Integer userIdx, Integer scheduleIdx, Date date) {
        Session session = sessionFactory.openSession();
        Schedule schedule = ss.get(scheduleIdx);
        Appointment temp = (Appointment) session.createCriteria(Appointment.class)
                .createAlias("schedule", "schedule")
                .createAlias("user", "user")
                .add(Restrictions.eq("date", date))
                .add(Restrictions.eq("user.useridx", userIdx))
                .add(Restrictions.eq("schedule.scheduleidx", scheduleIdx))
                .uniqueResult();

        if (temp == null) {
            session.saveOrUpdate(appointment);
            session.flush();
            session.close();
        }
        return appointment;
    }

    public boolean check(Integer size, Integer scheduleIdx, Date date) {
        Session session = sessionFactory.openSession();
        List<Appointment> lap = session.createCriteria(Appointment.class)
                .createAlias("schedule", "schedule")
                .add(Restrictions.eq("date", date))
                .add(Restrictions.eq("schedule.scheduleidx", scheduleIdx))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

      /*  Integer num=(Integer)session.createCriteria(Appointment.class)
                .createAlias("schedule","schedule")
                .add(Restrictions.eq("schedule.scheduleidx",scheduleIdx))
                .add(Restrictions.eq("date",date))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    */
        if (lap.size() == size) {
            for (Appointment ap : lap) {
                ap.setState(1);
                session.saveOrUpdate(ap);
            }
            session.flush();
            session.close();
            return true;
        }
        return false;
    }

    public void delete(Integer id) {
        Appointment Appointment = get(id);
        Session session = sessionFactory.openSession();
        session.delete(Appointment);
        session.flush();
        session.close();
    }

}

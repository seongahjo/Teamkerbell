package com.shape.web.service;

import com.shape.web.entity.FileDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Service
public class FileDBService {
    @Resource
    private SessionFactory sessionFactory;

    public FileDB get(Integer id) {
        Session session = sessionFactory.openSession();
        FileDB filedb = (FileDB) session.get(FileDB.class, id);
        session.close();
        return filedb;
    }

    public FileDB save(FileDB fileDB) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(fileDB);
        session.flush();
        session.close();
        return fileDB;
    }

    public void delete(Integer id) {
        FileDB fildb = get(id);
        Session session = sessionFactory.openSession();
        session.delete(fildb);
        session.flush();
        session.close();
    }

    public FileDB getByStoredname(String filename) {
        Session session = sessionFactory.openSession();
        FileDB filedb = (FileDB) session.createCriteria(FileDB.class).add(Restrictions.eq("storedname", filename)).uniqueResult();
        session.close();
        return filedb;
    }

    public FileDB getByOriginalname(Date date, String filename,String path) {
        Session session = sessionFactory.openSession();
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.DATE, 1); //startdate 날짜에 하루를 더함
        Date nextDate = cal.getTime();
        FileDB filedb = (FileDB) session.createCriteria(FileDB.class)
                .add(Restrictions.between("date", date, nextDate))
                .add(Restrictions.eq("path",path))
                .add(Restrictions.eq("originalname", filename))
                .uniqueResult();
        session.close();
        return filedb;
    }
}

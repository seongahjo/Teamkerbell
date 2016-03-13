package com.shape.web.service;

import com.shape.web.entity.Chatlog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChatlogService {
	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Chatlog> getAll(){
		Session session=sessionFactory.openSession();
		List<Chatlog> datas=(List<Chatlog>)session.createCriteria(Chatlog.class).list();
		return datas;
	}
	public Chatlog get(Integer id){
		Session session=sessionFactory.openSession();
		return (Chatlog)session.get(Chatlog.class, id);
	}
	
	
	public Chatlog getbyprojectidx(Integer projectidx){
		Session session=sessionFactory.openSession();
		return(Chatlog)session.createCriteria(Chatlog.class).add(Restrictions.eq("projectidx",projectidx)).uniqueResult();
	}
	public Chatlog add(String content, Integer projectidx,Date date){
		Chatlog Chatlog=new Chatlog();
		Session session=sessionFactory.openSession();
		Chatlog.setContent(content);
		Chatlog.setProjectidx(projectidx);
		Chatlog.setDate(date);
		session.save(Chatlog);
		session.flush();
		return Chatlog;
	}
	public Chatlog edit(Integer id,String content, Integer projectidx,Date date){
	Chatlog Chatlog=get(id);
	Session session=sessionFactory.openSession();
	Chatlog.setContent(content);
	Chatlog.setDate(date);
	Chatlog.setProjectidx(projectidx);
	session.update(Chatlog);
	session.flush();
	return Chatlog;
	}
	public void delete(Integer id){
		Chatlog Chatlog=get(id);
		Session session=sessionFactory.openSession();
		session.delete(Chatlog);
		session.flush();
	}
	
}

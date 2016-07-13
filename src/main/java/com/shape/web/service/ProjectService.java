package com.shape.web.service;

import com.shape.web.VO.MeetingMember;
import com.shape.web.VO.MemberGraph;
import com.shape.web.entity.Project;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ProjectService {
    @Autowired
    EntityManagerFactory entityManagerFactory;


    public List<MeetingMember> getMeetingMember(Project project) {

        Query query = entityManagerFactory.createEntityManager().createNativeQuery("SELECT s.scheduleidx,s.startdate as date," +
                "(SELECT GROUP_CONCAT(u.name) FROM Appointment ap JOIN User u on ap.useridx=u.useridx where s.scheduleidx = ap.scheduleidx and ap.state=3) as participant," +
                "(SELECT GROUP_CONCAT(u.name) FROM Appointment ap JOIN User u on ap.useridx=u.useridx where s.scheduleidx = ap.scheduleidx and ap.state=2) as nonparticipant," +
                "s.content, s.place " +
                "FROM Schedule s WHERE s.state=3 and s.projectidx =:projectidx");
        query.setParameter("projectidx", project.getProjectidx());
        //query.setResultTransformer(Transformers.aliasToBean(MeetingMember.class));
        /*
        Query query = session.createSQLQuery("SELECT s.scheduleidx,s.startdate as date," +
                "(SELECT GROUP_CONCAT(u.name) FROM Appointment ap JOIN User u on ap.useridx=u.useridx where s.scheduleidx = ap.scheduleidx and ap.state=3) as participant," +
                "(SELECT GROUP_CONCAT(u.name) FROM Appointment ap JOIN User u on ap.useridx=u.useridx where s.scheduleidx = ap.scheduleidx and ap.state=2) as nonparticipant," +
                "s.content, s.place "+
                "FROM Schedule s WHERE s.state=3 and s.projectidx =:projectidx");
       */
        List<MeetingMember> members = query.getResultList();
        //session.close();

        return members;
        //return null;
    }

    public List<MemberGraph> getMemberGraph(Project project) {
        Query query = entityManagerFactory.createEntityManager().createNativeQuery("SELECT u.useridx,u.name,count(if(td.OK=false,td.CONTENT,NULL))/count(td.OK)*100 as percentage,(count(if(ap.state=3,ap.scheduleidx,NULL))/count(if(ap.state>=2,ap.scheduleidx,NULL)))*100 as participate" +
                " FROM Appointment ap JOIN Schedule s on ap.scheduleidx = s.scheduleidx JOIN User u on ap.useridx = u.useridx JOIN Todolist td on u.USERIDX = td.USERIDX" +
                " WHERE s.projectidx=:projectidx and td.projectidx=:projectidx group by ap.useridx order by ap.useridx");
        query.setParameter("projectidx", project.getProjectidx());

        List<MemberGraph> graph = query.getResultList();
        return graph;

    }

}

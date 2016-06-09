package com.shape.web.service;

import com.shape.web.entity.*;
import com.shape.web.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Resource
    private SessionFactory sessionFactory;

    public User save(User user) {
        Session session = sessionFactory.openSession();
        session.merge(user);
        session.flush();
        session.close();
        return user;
    }

    public List<Todolist> getTodolist(User user) {
        Session session = sessionFactory.openSession();
        List<Todolist> lt = session.createCriteria(Todolist.class)
                .createAlias("user", "user")
                .add(Restrictions.eq("user.useridx", user.getUseridx()))
                .add(Restrictions.ge("enddate", new Date()))
                .addOrder(Order.desc("todolistidx"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return lt;
    }

    public User getById(String id) {
        Session session = sessionFactory.openSession();
        User u = (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        return u;
    }

    public User get(Integer useridx) {
        Session session = sessionFactory.openSession();
        User u = (User) session.get(User.class, useridx);
        session.close();
        return u;

    }

    public void addProject(Integer userIdx, Project project) {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, userIdx);
        user.addProject(project);
        session.update(user);
        session.flush();
        session.close();
    }

    public void deleteProject(Integer projectIdx) {
        Session session = sessionFactory.openSession();
        Project project = (Project) session.get(Project.class, projectIdx);
        for (User u : project.getUsers()) {
            u.getProjects().remove(project);
            session.merge(u);
        }
        session.delete(project);
        session.flush();
        session.close();
    }

    public User add(String id, String name, String pw, String img) {
        Session session = sessionFactory.openSession();
        User user = new User();
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

    /*
    User가 가지고 있는 Schedule 객체들을 반환
     */
    public List<Schedule> getScheudles(User user) {
        Session session = sessionFactory.openSession();
        List<Schedule> ls = session.createCriteria(Schedule.class)
                .createAlias("project", "project")
                .createAlias("project.users", "users")
                .add(Restrictions.eq("users.useridx", user.getUseridx()))
                .add(Restrictions.ge("enddate", new Date()))
                .addOrder(Order.desc("scheduleidx"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return ls;
    }

    /*
    User가 가지고 있는 Project 객체들을 반환
     */
    public List<Project> getProjects(User user){
        Session session = sessionFactory.openSession();
        List<Project> lp = session.createCriteria(Project.class)
                .createAlias("users", "users")
                .add(Restrictions.eq("users.useridx", user.getUseridx()))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("projectidx"))
                .list();
        session.close();
        return lp;
    }

    /*
    User가 가지고 있는 Alarm 객체들을 반환
     */
    public List<Alarm> getAlarms(Integer userIdx) {
        Session session = sessionFactory.openSession();
        List<Alarm> la = session.createCriteria(Alarm.class)
                .createAlias("user", "user")
                .add(Restrictions.eq("user.useridx", userIdx))
                .add(Restrictions.eq("isshow", true))
                .add(Restrictions.eq("contentid", 0))
                .addOrder(Order.asc("date"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        session.close();
        return la;
    }

    /*
    User가 가지고 있는 최신 알람객체 하나를 반환
     */
    public Alarm getOneAlarm(Integer userIdx) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select Alarm from Alarm as Alarm JOIN Alarm.user as User  with User.useridx = :useridx where  Alarm.contentid=0 and Alarm.isshow=true order by Alarm.date desc");
        query.setParameter("useridx", userIdx, StandardBasicTypes.INTEGER);
        query.setMaxResults(1);
        Alarm alarm = (Alarm) query.uniqueResult();
        session.close();
        return alarm;
    }

    /*
    User가 가지고 있는 Timeline을 반환
     */
    public List<Alarm> getTimeline(User user) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select Alarm from Alarm as Alarm JOIN Alarm.user as User  where User.useridx = :useridx  and Alarm.isshow = true and Alarm.contentid!=0 order by Alarm.date desc");
        query.setParameter("useridx", user.getUseridx(), StandardBasicTypes.INTEGER);
        query.setMaxResults(15);
        List<Alarm> la = query.list();
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

    /*
    Spring Security에서 권한 설정을 위한 객체 Role 반환
     */
    public List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
       /* if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        }*/
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
    /*
    Spring Security에서 로그인 인증을 위한 함수
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Session session = sessionFactory.openSession();
        User u = (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(u.getId(),
                u.getPw(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(0));
    }
}

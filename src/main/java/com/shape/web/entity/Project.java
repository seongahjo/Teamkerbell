package com.shape.web.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "PROJECTIDX")
    private Integer projectidx;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LEADERIDX")
    private Integer leaderidx;

    @Column(name = "MINUTE")
    private String minute;

    @ManyToMany(mappedBy = "projects",fetch = FetchType.EAGER )
    private Set<User> users = new HashSet<User>();


    @OneToMany(mappedBy = "project")
    private Set<Alarm> alarms = new HashSet<Alarm>();

    @OneToMany(mappedBy = "project")
    private Set<FileDB> filedbs = new HashSet<FileDB>();

    @OneToMany(mappedBy = "project")
    private Set<Minute> minutes = new HashSet<Minute>();


    @OneToMany(mappedBy = "project")
    @Sort
    private Set<Schedule> schedules = new HashSet<Schedule>();

    @OneToMany(mappedBy = "project")
    private Set<Todolist> todolists = new HashSet<Todolist>();

    public Set<Alarm> getAlarms() {
        return alarms;
    }

    public void addAlarms(Alarm alarm) {
        this.alarms.add(alarm);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Project() {
    }

    public Project(String name, Integer leaderidx, String minute) {
        this.name = name;
        this.leaderidx = leaderidx;
        this.minute = minute;
    }

    public Integer getProjectidx() {
        return projectidx;
    }

    public void setProjectidx(Integer projectidx) {
        this.projectidx = projectidx;
    }

    public Integer getLeaderidx() {
        return leaderidx;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLeaderidx(Integer leaderidx) {
        this.leaderidx = leaderidx;
    }

    public Set<FileDB> getFiledbs() {
        return filedbs;
    }

    public Set<Minute> getMinutes() {
        return minutes;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Set<Todolist> getTodolists() {
        return todolists;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}

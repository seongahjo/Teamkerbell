package com.shape.web.entity;





import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Project")
public class Project implements Serializable{
    private static final long serialVersionUID = 7463383057597003838L;
    @Id
    @GeneratedValue
    @Column(name = "PROJECTIDX")
    private Integer projectidx;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "LEADERIDX")
    private Integer leaderidx;

    @Column(name = "MINUTE")
    private String minute;

    @Column(name = "PROCESSED")
    private boolean processed = true;

    @Column(name="CREATEDAT")
    private Date createdat;

    @Column(name="UPDATEDAT")
    private Date updatedat;

    @JsonManagedReference
    @ManyToMany(mappedBy = "projects",fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<User>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Alarm> alarms = new HashSet<Alarm>();
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<FileDB> filedbs = new HashSet<FileDB>();
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Minute> minutes;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Schedule> schedules = new HashSet<Schedule>();
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Todolist> todolists = new HashSet<Todolist>();

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }

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
        minutes = new HashSet<Minute>();
    }

    public Project(String name, Integer leaderidx, String minute) {
        this.name = name;
        this.leaderidx = leaderidx;
        this.minute = minute;
        minutes = new HashSet<Minute>();
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public boolean equals(Object o ){
        Project pj=(Project)o;
        return this.projectidx == pj.getProjectidx();
    }

}

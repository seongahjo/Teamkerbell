package com.shape.web.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USERIDX")
    private Integer useridx;

    @Column(name = "ID")
    private String id;

    @Column(name = "PW")
    private String pw;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMG")
    private String img;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private Set<Alarm> alarmsactor = new HashSet<Alarm>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Alarm> alarmsuser = new HashSet<Alarm>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Todolist> todolists = new HashSet<Todolist>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<FileDB> filedbs = new HashSet<FileDB>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "useridx")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Participate",
            joinColumns = @JoinColumn(name = "USERIDX"),
            inverseJoinColumns = @JoinColumn(name = "PROJECTIDX")
    )
    private Set<Project> projects = new HashSet<Project>();


    public Set<Project> getProjects() {
        return projects;
    }

    public Set<Todolist> getTodolists() {
        return todolists;
    }

    public void addTodolists(Todolist todolist) {
        if (todolist.getUser() == null)
            todolist.setUser(this);
        this.todolists.add(todolist);
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointments(Appointment appointment) {
        if (appointment.getUser() == null)
            appointment.setUser(this);
        this.appointments.add(appointment);
    }

    public Set<FileDB> getFiledbs() {
        return filedbs;
    }

    public void addFiledbs(FileDB filedb) {
        if (filedb.getUser() == null)
            filedb.setUser(this);
        this.filedbs.add(filedb);
    }

    public Set<Alarm> getAlarmsactor() {
        return alarmsactor;
    }

    public void addAlarmsactor(Alarm alarmactor) {
        this.alarmsactor.add(alarmactor);
    }

    public Set<Alarm> getAlarmsuser() {
        return alarmsuser;
    }

    public void addAlarmsuser(Alarm alarmsuser) {
        this.alarmsuser.add(alarmsuser);
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }


    public User() {

    }

    public User(String id, String pw, String name, String img) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.img = img;
    }

    public Integer getUseridx() {
        return useridx;
    }

    public void setUseridx(Integer useridx) {
        this.useridx = useridx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPw() {
        return pw;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

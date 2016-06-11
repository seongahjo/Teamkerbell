package com.shape.web.entity;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "SCHEDULEIDX")
    private Integer scheduleidx;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "TIME")
    private Time time;

    @ManyToOne
    @JoinColumn(name="PROJECTIDX")
    private Project project;

    @Column(name = "STATE")
    private Integer state;
    // 0 register
    // 1 finish
    // 2 completion

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    @Type(type = "date")
    private Date startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    @Type(type = "date")
    private Date enddate;

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    public Schedule() {
    }



    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getScheduleidx() {
        return scheduleidx;
    }

    public void setScheduleidx(Integer scheduleidx) {
        this.scheduleidx = scheduleidx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}

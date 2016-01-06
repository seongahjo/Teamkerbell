package com.shape.web.entity;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue
    @Column(name = "APPOINTMENTIDX")
    private Integer appointmentidx;

    @ManyToOne
    @JoinColumn(name="USERIDX")
    private User user;

    @Column(name = "STATE")
    private Integer state;
    // 0 등록
    // 1 확정

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DATE")
    @Type(type = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SCHEDULEIDX")
    private Schedule schedule;

    public Appointment() {
    }

    public Integer getAppointmentidx() {
        return appointmentidx;
    }

    public void setAppointmentidx(Integer appointmentidx) {
        this.appointmentidx = appointmentidx;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Appointment(Integer state, Date date) {
        this.state = state;
        this.date = date;
    }
}

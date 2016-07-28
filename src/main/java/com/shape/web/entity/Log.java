package com.shape.web.entity;

/**
 * Created by seongahjo on 2016. 7. 16..
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Log")
public class Log {
    @Id
    @GeneratedValue
    @Column(name = "LOGIDX")
    private Integer logidx;

    @Column(name = "IP")
    private String ip;

    @ManyToOne
    @JoinColumn(name = "USERIDX")
    private User user;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name="UPDATEDAT")
    private Date updatedat;

    public Log() {
    }

    public Log(String ip) {
        this.ip = ip;
    }

    public Log(String ip, User user) {
        this.ip = ip;
        this.user = user;
    }

   public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }
   }

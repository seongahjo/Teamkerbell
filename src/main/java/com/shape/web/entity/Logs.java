package com.shape.web.entity;

/**
 * Created by seongahjo on 2016. 7. 16..
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "Logs")
public class Logs {
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

    public Logs() {
    }

    public Logs(String ip) {
        this.ip = ip;
    }

    public Logs(String ip, User user) {
        this.ip = ip;
        this.user = user;
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

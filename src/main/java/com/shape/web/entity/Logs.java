package com.shape.web.entity;

/**
 * Created by seongahjo on 2016. 7. 16..
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "Logs")
@EqualsAndHashCode(exclude={"user"})
public class Logs {
    @Id
    @GeneratedValue
    @Column(name = "LOGIDX")
    private Integer logidx;

    @Column(name = "IP")
    private String ip;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USERIDX")
    private User user;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name="UPDATEDAT")
    private Date updatedat;

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }

    public Logs() {
    }

    public Logs(String ip) {
        this.ip = ip;
    }

    public Logs(String ip, User user) {
        this.ip = ip;
        this.user = user;
    }


   }

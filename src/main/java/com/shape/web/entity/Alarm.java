package com.shape.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "Alarm")
@EqualsAndHashCode(exclude = {"project", "user", "actor"})
public class Alarm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "ALARMIDX")
    private Integer alarmidx;


    @Column(name = "CONTENTID")
    private Integer contentid;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "FILEURL")
    private String fileurl;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "ISSHOW")
    private boolean isshow = true;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name = "UPDATEDAT")
    private Date updatedat;

    @ManyToOne
    @JoinColumn(name = "PROJECTIDX")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "USERIDX")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ACTORIDX")
    private User actor;


    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }

    public Alarm(Integer contentid, String filename, String fileurl, Date date) {
        this.contentid = contentid;
        this.filename = filename;
        this.fileurl = fileurl;
        this.date = date;
    }

    public Alarm(Integer contentid, String filename, String fileurl, Date date, Project project, User user, User actor) {
        this.contentid = contentid;
        this.filename = filename;
        this.fileurl = fileurl;
        this.date = date;
        this.project = project;
        this.user = user;
        this.actor = actor;
    }

    public Alarm(Integer contentid, String filename, String fileurl, Date date, Project project, User actor) {
        this.contentid = contentid;
        this.filename = filename;
        this.fileurl = fileurl;
        this.date = date;
        this.project = project;
        this.actor = actor;
    }


}

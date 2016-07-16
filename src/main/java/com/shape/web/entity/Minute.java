package com.shape.web.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Minute")
public class Minute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "MINUTEIDX")
    private Integer minuteidx;

    @ManyToOne
    @JoinColumn(name="PROJECTIDX")
    private Project project;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "DATE")
    @Type(type="date")
    private Date date;

    @Column(name="CREATEDAT")
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

    public Minute() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getMinuteidx() {
        return minuteidx;
    }

    public void setMinuteidx(Integer minuteidx) {
        this.minuteidx = minuteidx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Minute( String content, Date date) {
        this.content = content;
        this.date = date;
    }
}

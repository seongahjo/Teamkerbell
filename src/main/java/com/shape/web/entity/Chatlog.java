package com.shape.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Chatlog")
public class Chatlog {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "PROJECTIDX")
    private Integer projectidx;

    @Column(name = "DATE")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getProjectidx() {
        return projectidx;
    }

    public void setProjectidx(int projectidx) {
        this.projectidx = projectidx;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

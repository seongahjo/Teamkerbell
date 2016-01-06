package com.shape.web.entity;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Todolist")
public class Todolist {
    @Id
    @GeneratedValue
    @Column(name = "TODOLISTIDX")
    private Integer todolistidx;


    @ManyToOne
    @JoinColumn(name = "USERIDX")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PROJECTIDX")
    private Project project;

    @Column(name = "OK")
    private boolean ok = true;


    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    @Type(type = "date")
    private Date startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    @Type(type = "date")
    private Date enddate;

    public Todolist() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {

        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (!user.getTodolists().contains(this))
            user.getTodolists().add(this);
        this.user = user;
    }

    public Integer getTodolistidx() {
        return todolistidx;
    }

    public void setTodolistidx(Integer todolistidx) {
        this.todolistidx = todolistidx;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean getOk() {
        return ok;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

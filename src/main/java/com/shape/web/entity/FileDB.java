package com.shape.web.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FileDB")
public class FileDB implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "FILEDBIDX")
    private Integer filedbidx;

    @ManyToOne
    @JoinColumn(name="PROJECTIDX")
    private Project project;

    @ManyToOne
    @JoinColumn(name="USERIDX")
    private User user;

    @Column(name = "STOREDNAME")
    private String storedname;

    @Column(name = "ORIGINALNAME")
    private String originalname;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PATH")
    private String path;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "DATE")
    private Date date;

    public Integer getFiledbidx() {
        return filedbidx;
    }

    public void setFiledbidx(Integer filedbidx) {
        this.filedbidx = filedbidx;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStoredname() {
        return storedname;
    }

    public void setStoredname(String storedname) {
        this.storedname = storedname;
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public FileDB(String storedname, String originalname, String type, String path, String tag, Date date) {
        this.storedname = storedname;
        this.originalname = originalname;
        this.type = type;
        this.path = path;
        this.tag = tag;
        this.date = date;
    }

    public FileDB() {
    }


}

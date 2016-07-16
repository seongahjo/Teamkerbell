package com.shape.web.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FileDB")
public class FileDB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "FILEDBIDX")
    private Integer filedbidx;


    @ManyToOne
    @JoinColumn(name="USERIDX")
    private User user;

    @ManyToOne
    @JoinColumn(name="PROJECTIDX")
    private Project project;

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

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public FileDB(String storedname, String originalname, String type, String path, String tag) {
        this.storedname = storedname;
        this.originalname = originalname;
        this.type = type;
        this.path = path;
        this.tag = tag;
    }

    public FileDB(User user, Project project, String storedname, String originalname, String type, String path, String tag) {
        this.user = user;
        this.project = project;
        this.storedname = storedname;
        this.originalname = originalname;
        this.type = type;
        this.path = path;
        this.tag = tag;
    }

    public FileDB() {
    }


}

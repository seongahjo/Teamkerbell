package com.shape.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@NamedNativeQuery(name="FileDB.groupbytest",
        query="select fd.originalname as Originalname, group_concat(distinct u.name) as Uploader, group_concat(distinct fd.tag) as tag from FileDB fd JOIN User u on fd.useridx=u.useridx JOIN Project p on fd.projectidx=p.projectidx where p.projectidx=?1 group by fd.originalname")
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

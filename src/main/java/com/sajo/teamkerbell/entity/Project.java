package com.sajo.teamkerbell.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Project")
@EqualsAndHashCode(of = {"projectId"})
@Data
@NoArgsConstructor
@ToString(exclude = "users")
public class Project implements Serializable {
    private static final long serialVersionUID = 7463383057597003838L;
    @Id
    @GeneratedValue
    @Column(name = "PROJECTID")
    private Integer projectId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "LEADERID")
    private Integer leaderId;

    @Column(name = "MINUTE")
    private String minute;

    @Column(name = "FINISHED")
    private boolean finished = true;

    @Column(name = "DELETED")
    private boolean deleted = false;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Column(name = "UPDATEDAT")
    private Date updatedAt;


    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


    public Project(String name, Integer leaderId, String minute) {
        this.name = name;
        this.leaderId = leaderId;
        this.minute = minute;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void finished() {
        this.finished = false;
    }

    public void deleted() {
        this.deleted = true;
    }

}

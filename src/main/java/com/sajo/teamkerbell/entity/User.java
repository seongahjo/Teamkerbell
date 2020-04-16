package com.sajo.teamkerbell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "User")
@EqualsAndHashCode(of = {"userId"})
public class User implements Serializable {
    private static final long serialVersionUID = 4870799528094495363L;

    @Id
    @GeneratedValue
    @Column(name = "USERID")
    private Integer userId;


    @Column(name = "ID")
    private String id;

    @Column(name = "PW")
    private String pw;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMG")
    private String img;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name = "UPDATEDAT")
    private Date updatedat;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "useridx")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Role role;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Participate",
            joinColumns = @JoinColumn(name = "USERID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECTID")
    )
    private Set<Project> projects = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }


    public User() {

    }

    public User(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public void addProject(Project project) {
        project.addUser(this);
        this.projects.add(project);
    }

    public void deleteProject(Project project) {
        project.removeUser(this);
        this.projects.remove(project);
    }

    public static User from(UserVO vo) {
        User u = new User(vo.getId(), vo.getPw(), vo.getName());
        u.role = new Role("user");
        return u;
    }

}

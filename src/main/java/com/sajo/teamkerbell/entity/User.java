package com.sajo.teamkerbell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@EqualsAndHashCode(of = {"userId"})
public class User implements Serializable {
    private static final long serialVersionUID = 4870799528094495363L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer userId;


    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String name;

    @Column
    private String img;

    @Column
    private LocalDate createdat;

    @Column
    private LocalDate updatedat;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "USERID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLEID", referencedColumnName = "id")}
    )
    private Role role;


    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Participate",
            joinColumns = @JoinColumn(name = "USERID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECTID")
    )
    private List<Project> projects = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = LocalDate.now();
    }


    public User() {

    }

    public User(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public void participateProject(Project project) {
        this.projects.add(project);
        project.addUser(this);
    }

    public void leaveProject(Project project) {
        this.projects.remove(project);
        project.removeUser(this);
    }

    public static User from(UserVO vo) {
        User u = new User(vo.getId(), vo.getPw(), vo.getName());
        u.role = new Role("user");
        return u;
    }


}

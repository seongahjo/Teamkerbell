package com.shape.web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "User")
//@EqualsAndHashCode(exclude={"useridx","createdat","updatedat"})

/*
Todolist,
Invite경우 UserId로 검색.
 */
@EqualsAndHashCode(exclude={"alarmsactor","alarmsuser","logs","todolists","filedbs","role"})
public class User implements Serializable {
    private static final long serialVersionUID = 4870799528094495363L;
    @Id
    @GeneratedValue
    @Column(name = "USERIDX")
    private Integer useridx;


    @Column(name = "ID")
    @NotEmpty
    @Size(min = 4, max = 10)
    private String id;

    @Column(name = "PW")
    @NotEmpty
    @Size(min = 4, max = 10)
    private String pw;

    @Column(name = "NAME")
    @NotEmpty
    private String name;

    @Column(name = "IMG")
    private String img;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name = "UPDATEDAT")
    private Date updatedat;


    @JsonIgnore
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private Set<Alarm> alarmsactor = new HashSet<Alarm>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Alarm> alarmsuser = new HashSet<Alarm>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Logs> logs = new HashSet<Logs>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Todolist> todolists = new HashSet<Todolist>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<FileDB> filedbs = new HashSet<FileDB>();
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
            joinColumns = @JoinColumn(name = "USERIDX"),
            inverseJoinColumns = @JoinColumn(name = "PROJECTIDX")
    )
    private Set<Project> projects = new HashSet<Project>();

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

    public User(String id, String pw, String name, String img) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.img = img;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }
    public void deleteProject(Project project) {this.projects.remove(project);}

}

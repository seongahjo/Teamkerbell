package com.shape.web.entity;





import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shape.web.VO.MemberGraph;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Project")
@EqualsAndHashCode(of={"projectidx"},exclude={"users"})
@NamedNativeQuery(name="Project.todolistPercentage",
        query="SELECT u.useridx,u.name,count(if(td.OK=false,td.CONTENT,NULL))/count(td.OK)*100 as percentage" +
                " FROM Todolist td JOIN User u on td.useridx = u.useridx" +
                " WHERE td.projectidx=?1 group by td.useridx order by td.useridx")

@Data
public class Project implements Serializable{
    private static final long serialVersionUID = 7463383057597003838L;
    @Id
    @GeneratedValue
    @Column(name = "PROJECTIDX")
    private Integer projectidx;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "LEADERIDX")
    private Integer leaderidx;

    @Column(name = "MINUTE")
    private String minute;

    @Column(name = "PROCESSED")
    private boolean processed = true;

    @Column(name="CREATEDAT")
    private Date createdat;

    @Column(name="UPDATEDAT")
    private Date updatedat;

  @JsonIgnore
    @ManyToMany(mappedBy = "projects",fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<User>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Alarm> alarms = new HashSet<Alarm>();
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<FileDB> filedbs = new HashSet<FileDB>();
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Minute> minutes;



    @OneToMany(mappedBy = "project")
    private Set<Schedule> schedules = new HashSet<Schedule>();

    @OneToMany(mappedBy = "project")
    private Set<Todolist> todolists = new HashSet<Todolist>();

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }

    public Project() {
        minutes = new HashSet<Minute>();
    }

    public Project(String name, Integer leaderidx, String minute) {
        this.name = name;
        this.leaderidx = leaderidx;
        this.minute = minute;
        minutes = new HashSet<Minute>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }


}

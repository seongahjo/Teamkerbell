package com.sajo.teamkerbell.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@EqualsAndHashCode(of = {"projectId"})
@Data
@NoArgsConstructor
@ToString(exclude = "users")
public class Project implements Serializable {
    private static final long serialVersionUID = 7463383057597003838L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer projectId;

    @NotNull
    @Column
    private String name;

    @Column
    private Integer leaderId;

    @Column
    private String minute;

    @Column
    private boolean finished = false;

    @Column
    private boolean deleted = false;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;


    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }


    public Project(String name, Integer leaderId, String minute) {
        this.name = name;
        this.leaderId = leaderId;
        this.minute = minute;
    }

    void addUser(User user) {
        this.users.add(user);
    }

    void removeUser(User user) {
        this.users.remove(user);
    }

    public void finished() {
        this.finished = true;
    }

    public void deleted() {
        this.deleted = true;
    }

    public void updateMinute(Minute minute) {
        this.minute = minute != null ? minute.getContent() : " ";
    }
}

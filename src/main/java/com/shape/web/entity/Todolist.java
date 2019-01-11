package com.shape.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "Todolist")
@EqualsAndHashCode(exclude = {"project", "user"})
public class Todolist implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Column(name = "overdue")
    private boolean overdue = false;


    @NotNull
    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    @Type(type = "date")
    private Date startdate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    @Type(type = "date")
    private Date enddate;

    @Column(name = "CREATEDAT")
    private Date createdat;

    @Column(name = "UPDATEDAT")
    private Date updatedat;

    @PrePersist
    protected void onCreate() {
        updatedat = createdat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = new Date();
    }



}

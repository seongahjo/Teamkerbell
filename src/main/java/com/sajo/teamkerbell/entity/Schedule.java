package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "Schedule")
@EqualsAndHashCode(exclude = {"project"})
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "SCHEDULEIDX")
    private Integer scheduleidx;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "TIME")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "PROJECTIDX")
    private Project project;

    @Column(name = "STATE")
    private Integer state;
    // 0 register
    // 1 finish
    // 2 completion

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    @Type(type = "date")
    private Date startdate;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Appointment> appointments = new HashSet<>();


}

package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Minute")
@Data
@EqualsAndHashCode(exclude = {"project"})
public class Minute implements Serializable, Comparable<Minute> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "MINUTEIDX")
    private Integer minuteidx;

    @ManyToOne
    @JoinColumn(name = "PROJECTIDX")
    private Project project;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "DATE")
    @Type(type = "date")
    private Date date;

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

    public Minute() {
    }


    public Minute(String content, Date date) {
        this.content = content;
        this.date = date;
    }


    @Override
    public int compareTo(Minute m) {
        return m.getDate().after(getDate()) ? 1 : -1;
    }
}

package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "Minute")
@Data
@EqualsAndHashCode
public class Minute implements Serializable, Comparable<Minute> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "MINUTEID")
    private Integer minuteId;

    @Column(name = "PROJECTID")
    private Integer projectId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "CREATEDAT")
    private LocalDate createdAt;

    @Column(name = "UPDATEDAT")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Minute() {
    }

    public Minute(Integer projectId, String content, LocalDate date) {
        this.projectId = projectId;
        this.content = content;
        this.date = date;
    }


    @Override
    public int compareTo(Minute m) {
        return m.date.compareTo(date);
    }
}

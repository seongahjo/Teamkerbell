package com.sajo.teamkerbell.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sajo.teamkerbell.vo.ScheduleVO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "Schedule")
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "SCHEDULEID")
    private Integer scheduleId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "PROJECTID")
    private Integer projectId;

    @Column(name = "STATE")
    @Enumerated
    private ScheduleState state;
    // 0 register
    // 1 finish
    // 2 completion

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    private LocalDate endDate;

    @Column(name = "CREATEDAT")
    private LocalDate createdAt;

    @Column(name = "UPDATEDAT")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Appointment> appointments = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Schedule(Integer projectId, String content, String place, Time time, ScheduleState state, LocalDate startDate, LocalDate endDate) {
        this.projectId = projectId;
        this.content = content;
        this.place = place;
        this.time = time;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void finished() {
        this.state = ScheduleState.FINISH;
    }

    public void completed() {
        this.state = ScheduleState.COMPLETION;
    }

    public enum ScheduleState {
        REGISTER,
        FINISH,
        COMPLETION
    }


    public static Schedule from(Integer projectId, ScheduleVO scheduleVO) {
        return new Schedule(
                projectId,
                scheduleVO.getContent(),
                scheduleVO.getPlace(),
                scheduleVO.getTime(),
                ScheduleState.REGISTER,
                scheduleVO.getStartDate(),
                scheduleVO.getEndDate()
        );
    }

    public void update(ScheduleVO scheduleVO) {
        this.content = scheduleVO.getContent();
        this.place = scheduleVO.getContent();
        this.time = scheduleVO.getTime();
        this.startDate = scheduleVO.getStartDate();
        this.endDate = scheduleVO.getEndDate();
    }

    public void add(Appointment appointment) {
        this.appointments.add(appointment);
    }

}

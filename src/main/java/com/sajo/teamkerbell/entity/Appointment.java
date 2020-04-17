package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Appointment")
@Data
@NoArgsConstructor
@ToString(exclude = "schedule")
public class Appointment implements Serializable {
    /*
    appointment
    state
    0 : 사용자가 등록한 상태
    1 : 모든 사용자가 선택한 날짜
    2 : 날짜 확정된 상태 (schedule state가 3일경우 non-participant)
    3 : 참가한 상태
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "APPOINTMENTID")
    private Integer appointmentId;

    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "STATE")
    @Enumerated
    private AppointmentState state;
    // 0 등록
    // 1 확정

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHEDULEIDX")
    private Schedule schedule;

    public enum AppointmentState {
        CREATED,
        CONFIRMED
    }

    public Appointment(Integer userId) {
        this.userId = userId;
        this.state = AppointmentState.CREATED;
    }

    public void assignTo(Schedule schedule) {
        this.schedule = schedule;
        schedule.add(this);
    }

}

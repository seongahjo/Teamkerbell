package com.sajo.teamkerbell.entity;

import com.sajo.teamkerbell.vo.AlarmVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alarmId;

    @Column
    private Integer inviteeId; // 초대받은 사람

    @Column
    private Integer inviterId; //초대한 사람
    // 초대한 사람
    // 초대 받은 사람
    // 프로젝트 아이디

    @Column
    private Integer projectId;

    @Column
    private boolean active = true;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    public static Alarm from(AlarmVO alarmVO) {
        return new Alarm(alarmVO.getInviteeId(), alarmVO.getInviterId(), alarmVO.getProjectId());

    }

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Alarm(Integer inviteeId, Integer inviterId, Integer projectId) {
        this.inviteeId = inviteeId;
        this.inviterId = inviterId;
        this.projectId = projectId;
    }

    public void decline() {
        active = false;
    }

    public void accept(User invitee, Project project) {
        invitee.participateProject(project);
    }


}

package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AlarmVO {
    @NotNull
    private Integer inviteeId;
    @NotNull
    private Integer inviterId;
    @NotNull
    private Integer projectId;

    public AlarmVO(Integer inviteeId, Integer inviterId, Integer projectId) {
        this.inviteeId = inviteeId;
        this.inviterId = inviterId;
        this.projectId = projectId;
    }
}

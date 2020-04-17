package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleVO {
    @NotNull
    private String content;
    @NotNull
    private String place;
    @NotNull
    private Time time;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    public ScheduleVO(String content, String place, Time time, LocalDate startDate, LocalDate endDate) {
        this.content = content;
        this.place = place;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

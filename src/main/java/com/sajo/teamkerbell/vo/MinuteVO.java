package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MinuteVO {
    @NotNull
    private String content;
    @NotNull
    private LocalDate date;

    public MinuteVO(String content, LocalDate now) {
        this.content = content;
        this.date = now;
    }
}

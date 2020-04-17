package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TodoListVO {
    @NotNull
    private String content;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private Integer userId;
    private Integer projectId;

    public TodoListVO(String content, LocalDate startDate, LocalDate endDate, Integer userId, Integer projectId) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.projectId = projectId;
    }
}

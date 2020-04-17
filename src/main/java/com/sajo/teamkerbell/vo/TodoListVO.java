package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class TodoListVO {
    @NotNull
    private String content;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private Integer userId;
    private Integer projectId;

    public TodoListVO(String content, Date startDate, Date endDate, Integer userId, Integer projectId) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.projectId = projectId;
    }
}

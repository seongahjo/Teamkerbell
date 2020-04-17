package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timelineId;

    @Column
    private String content;

    @Column
    private Integer projectId;


    private Timeline(String content, Integer projectId) {
        this.content = content;
        this.projectId = projectId;
    }

    public static Timeline from(Schedule schedule) {
        return new Timeline(schedule.toTimeline(), schedule.getProjectId());
    }

    public static Timeline from(TodoList todoList) {
        return new Timeline(todoList.toTimeline(), todoList.getProjectId());
    }

    public static Timeline from(Minute minute) {
        return new Timeline(minute.toTimeline(), minute.getProjectId());
    }

    public static Timeline from(FileDB fileDB) {
        return new Timeline(fileDB.toTimeline(), fileDB.getProjectId());
    }

}

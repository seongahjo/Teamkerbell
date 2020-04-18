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

    public static Timeline from(TimelineAdapter timeline) {
        return new Timeline(timeline.toTimeline(), timeline.toProjectId());
    }

}

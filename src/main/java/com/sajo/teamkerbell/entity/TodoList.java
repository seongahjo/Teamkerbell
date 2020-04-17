package com.sajo.teamkerbell.entity;

import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "TodoList")
@EqualsAndHashCode(of = "todoListId")
@NoArgsConstructor
public class TodoList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "TODOLISTID")
    private Integer todoListId;


    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "PROJECTID")
    private Integer projectId;

    @Column(name = "FINISHED")
    private boolean finished = false;

    @Column(name = "overdue")
    private boolean overdue = false;


    @NotNull
    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "STARTDATE")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    private LocalDate endDate;

    @Column(name = "CREATEDAT")
    private LocalDate createdAt;

    @Column(name = "UPDATEDAT")
    private LocalDate updatedAt;

    public TodoList(String content, LocalDate startDate, LocalDate endDate, Integer projectId, Integer userId) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.userId = userId;
        this.overdue(LocalDate.now());
    }

    public void check() {
        this.finished = !this.finished;
    }

    public void overdue(LocalDate nowDate) {
        if (nowDate.isAfter(this.endDate))
            this.overdue = true;
    }

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public static TodoList from(int projectId, TodoListVO todoListVO) {
        return new TodoList(
                todoListVO.getContent(),
                todoListVO.getStartDate(),
                todoListVO.getEndDate(),
                projectId,
                todoListVO.getUserId());
    }

    public void update(TodoListVO todoListVO) {
        this.userId = todoListVO.getUserId() == null ? this.userId : todoListVO.getUserId();
        this.content = todoListVO.getContent();
        this.startDate = todoListVO.getStartDate();
        this.endDate = todoListVO.getEndDate();
        this.overdue(LocalDate.now());
    }

}

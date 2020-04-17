package com.sajo.teamkerbell.entity;

import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


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
    @Type(type = "date")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ENDDATE")
    @Type(type = "date")
    private Date endDate;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Column(name = "UPDATEDAT")
    private Date updatedAt;

    public TodoList(String content, Date startDate, Date endDate, Integer projectId, Integer userId) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.userId = userId;
        this.overdue(new Date());
    }

    public void check() {
        this.finished = !this.finished;
    }

    public void overdue(Date nowDate) {
        if (nowDate.after(this.endDate))
            this.overdue = true;
    }

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public static TodoList from(TodoListVO todoListVO) {
        return new TodoList(
                todoListVO.getContent(),
                todoListVO.getStartDate(),
                todoListVO.getEndDate(),
                todoListVO.getProjectId(),
                todoListVO.getUserId());
    }

    public void update(TodoListVO todoListVO) {
        this.userId = todoListVO.getUserId() == null ? this.userId : todoListVO.getUserId();
        this.content = todoListVO.getContent();
        this.startDate = todoListVO.getStartDate();
        this.endDate = todoListVO.getEndDate();
        this.overdue(new Date());
    }

}

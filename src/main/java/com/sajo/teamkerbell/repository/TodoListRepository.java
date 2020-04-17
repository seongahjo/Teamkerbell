package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.TodoList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Integer> {
    @Query("SELECT t from TodoList t left join Project p on p.projectId = t.projectId where t.userId = ?1 and p.finished = false")
    List<TodoList> findByUserId(Integer userId, Pageable pageable);

    List<TodoList> findByProjectId(Integer projectId, Pageable page);
}

package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.TodoList;
import com.sajo.teamkerbell.repository.TodoListRepository;
import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;

    public TodoList check(Integer todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId).orElseThrow(IllegalArgumentException::new);
        todoList.check();
        return todoList;
    }

    public List<TodoList> getYetTodoListsFromUser(Integer userId, int page, int size) {
        return todoListRepository
                .findByUserId(userId, PageRequest.of(page, size)).stream()
                .filter(t -> !t.isFinished()).collect(Collectors.toList());
    }

    public List<TodoList> getAllTodoListsFromProjectId(Integer projectId, int page, int size) {
        return todoListRepository.findByProjectId(projectId, PageRequest.of(page, size));
    }

    public TodoList getTodoListById(Integer id) {
        return todoListRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public TodoList save(Integer projectId, TodoListVO t) {
        TodoList todoList = TodoList.from(projectId, t);
        return todoListRepository.save(todoList);
    }

    public TodoList update(Integer todoListId, TodoListVO t) {
        TodoList todoList = todoListRepository.findById(todoListId).orElseThrow(IllegalArgumentException::new);
        todoList.update(t);
        return todoList;
    }
}

package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.entity.TodoList;
import com.sajo.teamkerbell.service.TodoListService;
import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by seongahjo on 2016. 6. 14..
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping(value = "/user/{userId}/todoList")
    public ResponseEntity<List<TodoList>> getTodoListsFromUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(todoListService.getYetTodoListsFromUser(userId, page, size));
    }

    @GetMapping(value = "/project/{projectId}/todoList")
    public ResponseEntity<List<TodoList>> getTodoListFromProject(
            @PathVariable("projectId") Integer projectId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(todoListService.getAllTodoListsFromProjectId(projectId, page, size));
    }

    @PostMapping(value = "/project/{projectId}/todoList")
    public ResponseEntity<TodoList> makeTodoList(
            @PathVariable("projectId") Integer projectId,
            @RequestBody @Valid TodoListVO todoListVO,
            BindingResult result) {
        if (result.hasErrors())
            throw new IllegalArgumentException();

        return ResponseEntity.ok(todoListService.save(projectId, todoListVO));
    }

    @PutMapping(value = "/todoList/{todoListId}")
    public ResponseEntity<Void> updateTodoList(
            @PathVariable("todoListId") Integer todoListId,
            @RequestBody @Valid TodoListVO todoListVO,
            BindingResult result) {
        if (result.hasErrors())
            throw new IllegalArgumentException();
        todoListService.update(todoListId, todoListVO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/todoList/{todoListId}/check")
    public ResponseEntity<Void> checkTodoList(@PathVariable(value = "todoListId") Integer todoListId) {
        todoListService.check(todoListId);
        return ResponseEntity.ok().build();
    }
}

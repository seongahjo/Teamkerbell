package com.shape.web.controller;

import com.shape.web.entity.Project;
import com.shape.web.entity.Todolist;
import com.shape.web.entity.User;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.TodolistRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.ProjectService;
import com.shape.web.service.TodolistService;
import com.shape.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by seongahjo on 2016. 6. 14..
 */
@RestController
public class TodolistController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TodolistRepository todolistRepository;

    @Autowired
    TodolistService todolistService;
    /*
   To make to-do list
   */
    @RequestMapping(value = "/todolist", method = RequestMethod.POST)
    public ResponseEntity makeTodolist(@RequestParam("projectIdx") Integer projectIdx,
                                       @RequestParam("userId") String userId,
                                       @ModelAttribute("todolist") @Valid Todolist todolist,
                                       BindingResult result) {
        if(!result.hasErrors()) {
            Project project = projectService.getProject(projectIdx);
            User user = userService.getUserById(userId);

            todolist.setProject(project); // todolist가 어디 프로젝트에서 생성되었는가
            todolist.setUser(user); // todolist가 누구것인가
            todolistService.save(todolist); // todolist 생성
            logger.info("todolist 만듬");
            return new ResponseEntity(HttpStatus.CREATED);
        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/todolist", method = RequestMethod.PUT)
    public void modifyTodolist(@RequestBody Todolist todolist) {
        Todolist t = todolistRepository.findOne(todolist.getTodolistidx());
        if (todolist.getStartdate() != null)
            t.setStartdate(todolist.getStartdate());
        if (todolist.getEnddate() != null)
            t.setEnddate(todolist.getEnddate());
        if (todolist.getContent() != null)
            t.setContent(t.getContent());
        todolistService.save(t);

    }

    /*
     To accomplish to-do list
     */
    @RequestMapping(value = "/todocheck", method = RequestMethod.GET)
    public void todocheck(@RequestParam(value = "id") Integer id) {
        Todolist todolist = todolistRepository.findOne(id);
        todolist.setOk(!todolist.getOk());
        todolistService.save(todolist);
    }
}

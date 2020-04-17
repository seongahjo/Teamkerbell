package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/*
  Handles requests for the project.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping(value = "/user/{userId}/projects")    //프로젝트 반환
    public ResponseEntity<List<Project>> getProjectsFromUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        List<Project> projects = projectService.getProjectByUserId(userId, page, size);
        if (projects.isEmpty())
            return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(projects);
    }

    /*
    To delete project room
    */
    @DeleteMapping(value = "/project/{projectId}")    //프로젝트 삭제
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") Integer projectId) {
        projectService.delete(projectId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/project/{projectIdx}")    //프로젝트 업데이트
    public ResponseEntity<Void> updadeProject(@PathVariable("projectIdx") Integer projectIdx) {
        projectService.finish(projectIdx);
        return ResponseEntity.ok().build();
    }
}

package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project save(Project p) {
        return projectRepository.save(p);
    }

    public List<Project> getProjectByUserId(Integer userId, int page, int size) {
        return projectRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    public Project getCurrentProjectId(Integer projectId, Minute minute) {
        Project project = projectRepository.findById(projectId).orElseThrow(IllegalStateException::new);
        project.updateMinute(minute);
        return project;
    }


    public Project finish(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);
        project.finished();
        return project;
    }

    public Project delete(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);
        project.deleted();
        return project;
    }
}

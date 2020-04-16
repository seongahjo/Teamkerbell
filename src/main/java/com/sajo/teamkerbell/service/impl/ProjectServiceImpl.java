package com.sajo.teamkerbell.service.impl;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project getProject(Integer projectIdx) {
        return projectRepository.findOne(projectIdx);
    }


    @Override
    public List<Project> getProjects(User u) {
        return projectRepository.findByUsers(u);
    }

    @Override
    public List<Project> getProjects(User u, Integer page, Integer count) {
        return projectRepository.findByUsers(u, new PageRequest(page, count));
    }

    @Override
    public Project save(Project p) {
        return projectRepository.save(p);
    }

    @Override
    public void delete(Integer projectId) {
        projectRepository.delete(projectId);
    }

}

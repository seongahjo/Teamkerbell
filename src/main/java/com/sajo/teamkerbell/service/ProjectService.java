package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    Project getProject(Integer projectIdx);

    List<Project> getProjects(User u);

    List<Project> getProjects(User u, Integer page, Integer count);

    Project save(User u, Project p);

    void delete(User u, Integer p);


}

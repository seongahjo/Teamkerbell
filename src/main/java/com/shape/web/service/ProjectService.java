package com.shape.web.service;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
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

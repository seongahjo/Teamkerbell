package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    public void save(Project p, User u ){
        userRepository.saveAndFlush(u);
        projectRepository.saveAndFlush(p);
    }
    public void save(Project p){
        projectRepository.saveAndFlush(p);
    }
}

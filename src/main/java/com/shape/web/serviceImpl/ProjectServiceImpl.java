package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.ProjectRepository;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;


    @Override
    @Cacheable(value = "project", key = "'project:'.concat(#p0)")
    public Project getProject(Integer projectIdx) {
        return projectRepository.findOne(projectIdx);
    }



    @Override
    @Cacheable(value = "projects", key = "'user:'.concat(#p0.id).concat(':projects')")
    public List getProjects(User u) {
        return projectRepository.findByUsers(u);
    }

    @Override
    //@Cacheable(value = "projects", key = "'user:'.concat(#p0.id).concat(':projects').concat(#p1)")
    public List getProjects(User u, Integer page, Integer count) {
        return projectRepository.findByUsers(u,new PageRequest(page,count));
    }

    @Override
    @Caching(evict={
            @CacheEvict(value = "projects", key = "'user:'.concat(#p0.id).concat(':projects')"),
            @CacheEvict(value="project",key="'project:'.concat(#p1.projectidx)")
    })
    public Project save(User u,Project p) {
        userRepository.saveAndFlush(u);
       return projectRepository.saveAndFlush(p);
    }

    @Override
    @Caching(evict={
            @CacheEvict(value = "projects", key = "'user:'.concat(#p0.id).concat(':projects')"),
            @CacheEvict(value="project",key="'project:'.concat(#p1.projectidx)")
    })
    public void delete(Integer p) {
         projectRepository.delete(p);
    }

    /*@Override
    public void save(Project p) {
        projectRepository.saveAndFlush(p);
    }*/
}

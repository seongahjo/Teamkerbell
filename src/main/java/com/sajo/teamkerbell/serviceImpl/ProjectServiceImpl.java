package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.service.ProjectService;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {


    private UserRepository userRepository;

    private ProjectRepository projectRepository;


    @Autowired
    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Cacheable(value = "project", key = "'project:'.concat(#p0)")
    public Project getProject(Integer projectIdx) {
        return projectRepository.findOne(projectIdx);
    }


    @Override
    public List<Project> getProjects(User u) {
        return projectRepository.findByUsers(u);
    }

    // paging시 cache는 어떤식으로 할까?
    @Override
    //@Cacheable(value = "projects", key = "'user:'.concat(#p0.id).concat(':projects').concat(#p1)")
    public List<Project> getProjects(User u, Integer page, Integer count) {
        return projectRepository.findByUsers(u, new PageRequest(page, count));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects", key = "'user:'.concat(#p0.useridx).concat(':projects')"),
            @CacheEvict(value = "users", key = "'users:'.concat(#p1.projectidx).concat(':projects')"),
            @CacheEvict(value = "project", key = "'project:'.concat(#p1.projectidx)"),
            @CacheEvict(value = "user", key = "'user:'.concat(#p0.useridx)"),
            @CacheEvict(value = "user", key = "'uesr:'.concat(#p0.id).concat(':id')")
    })
    public Project save(User u, Project p) {
        p = projectRepository.saveAndFlush(p);
        userRepository.saveAndFlush(u);
        return p;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects", key = "'user:'.concat(#p0.useridx).concat(':projects')"),
            @CacheEvict(value = "users", key = "'users:'.concat(#p1).concat(':projects')"),
            @CacheEvict(value = "project", key = "'project:'.concat(#p1)"),
            @CacheEvict(value = "user", key = "'user:'.concat(#p0.useridx)"),
            @CacheEvict(value = "user", key = "'uesr:'.concat(#p0.id).concat(':id')")
    })
    public void delete(User u, Integer p) {
        userRepository.saveAndFlush(u);
        projectRepository.delete(p);
    }

}

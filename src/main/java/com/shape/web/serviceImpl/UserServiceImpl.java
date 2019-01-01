package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "user", key = "'user:'.concat(#p0).concat(':id')")
    public User getUser(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Cacheable(value = "user", key = "'user:'.concat(#p0)")
    public User getUser(Integer useridx) {
        return userRepository.findOne(useridx);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "user", key = "'user:'.concat(#p0.useridx)"),
            @CacheEvict(value = "user", key = "'uesr:'.concat(#p0.id).concat(':id')")
    })

    public User save(User u) {
        return userRepository.saveAndFlush(u);
    }

    // user는 다 가지고 있는데 삭제할경우 잘되려나

    @Override
    @Cacheable(value = "users", key = "'users:'.concat(#p0.projectidx).concat(':projects')")
    public List<User> getUsersByProject(Project p) {
        return userRepository.findByProjects(p);
    }


}

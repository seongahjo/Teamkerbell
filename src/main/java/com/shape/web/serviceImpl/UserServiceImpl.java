package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /*
        user:'idx'
        user:'id':id
        users:'projectidx':projects =>projects
     */
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
    public List getUsersByProject(Project p) {
        return userRepository.findByProjects(p);
    }


}

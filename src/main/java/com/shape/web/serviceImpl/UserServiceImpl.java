package com.shape.web.serviceImpl;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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




    @Override
    @Cacheable(value = "user", key = "'user:'.concat(#p0)")
    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "user", key = "'user:'.concat(#p0.id())")
    public User save(User u) {
        return userRepository.saveAndFlush(u);
    }

    @Override
    @Cacheable(value = "users", key = "'users:'.concat(#p0.projectidx)")
    public List getUsersByProject(Project p) {
        return userRepository.findByProjects(p);
    }


}

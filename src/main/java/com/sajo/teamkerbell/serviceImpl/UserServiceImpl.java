package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUser(Integer useridx) {
        return userRepository.findOne(useridx);
    }

    @Override

    public User save(User u) {
        return userRepository.saveAndFlush(u);
    }

    // user는 다 가지고 있는데 삭제할경우 잘되려나

    @Override
    @Cacheable(value = "users", key = "'users:'.concat(#p0.projectidx).concat(':projects')")
    public List<User> getUsersByProject(Project p) {
        return userRepository.findByProjects(p);
    }

    @Override
    public boolean isRegistable(User u) {
        User user = userRepository.findById(u.getId());
        return user == null;
    }


}

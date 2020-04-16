package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 26..
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findById(userId);
    }

    public User getUser(Integer userId) {
        return userRepository.findOne(userId);
    }

    public User save(User u) {
        return userRepository.save(u);
    }

    public List<User> getUsersByProject(Project p) {
        return userRepository.findByProjects(p);
    }

    public boolean isExist(UserVO u) {
        User user = userRepository.findById(u.getId());
        return user == null;
    }


}

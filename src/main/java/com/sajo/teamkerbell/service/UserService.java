package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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

    public User getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User getUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    public User save(User u) {
        return userRepository.save(u);
    }

    public List<User> getUsersFromProject(Integer projectId, int page, int size) {
        return userRepository.findByProjectId(projectId, PageRequest.of(page, size));
    }

    public User searchProjectCandidate(Integer userId, Integer projectId) {
        User u = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        boolean participated = u.getProjects().stream().map(Project::getProjectId).anyMatch(projectId::equals);
        return participated ? u : null;
    }

    public boolean isExist(UserVO u) {
        User user = userRepository.findById(u.getId());
        return user == null;
    }


}

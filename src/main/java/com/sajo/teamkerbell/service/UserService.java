package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUser(String id);

    User getUser(Integer useridx);

    User save(User u);

    List<User> getUsersByProject(Project p);

    boolean isExist(UserVO u);
}

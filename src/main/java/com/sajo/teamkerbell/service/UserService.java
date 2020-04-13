package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService  {
    User getUser(String id);

    User getUser(Integer useridx);

    User save(User u);

    List<User> getUsersByProject(Project p);

    boolean isRegistable(User u);
}

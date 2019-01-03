package com.shape.web.service;

import com.shape.web.entity.Project;
import com.shape.web.entity.User;
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

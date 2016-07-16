package com.shape.web.service;

import com.shape.web.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@Service
public interface LogService {
    public void addLog(String ip,User u);

}

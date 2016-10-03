package com.shape.web.service;

import com.shape.web.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@Service
public interface LogsService {
    public void addLog(String ip,User u);

}

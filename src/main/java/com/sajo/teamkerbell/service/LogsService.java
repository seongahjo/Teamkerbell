package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@Service
public interface LogsService {
    void addLog(String ip,String content,User u);

}

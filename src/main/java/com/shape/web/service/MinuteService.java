package com.shape.web.service;

import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface MinuteService {
    List getMinutes(Project p);
    Minute save(Minute m);
}

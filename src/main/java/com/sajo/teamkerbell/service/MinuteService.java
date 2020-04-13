package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.entity.Project;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public interface MinuteService {
    List<Minute> getMinutes(Project p);

    Minute getMinute(Project p,Date date);

    Minute save(Minute m);
}

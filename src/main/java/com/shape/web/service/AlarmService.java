package com.shape.web.service;

import com.shape.web.entity.Alarm;
import org.springframework.stereotype.Service;

/**
 * Created by seongahjo on 2016. 7. 18..
 */
@Service
public interface AlarmService {
    Alarm create(Alarm alarm);
}

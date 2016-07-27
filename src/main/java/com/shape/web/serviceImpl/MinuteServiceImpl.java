package com.shape.web.serviceImpl;

import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import com.shape.web.repository.MinuteRepository;
import com.shape.web.service.MinuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public class MinuteServiceImpl implements MinuteService{
    @Autowired
    MinuteRepository minuteRepository;
    @Override
    @Cacheable(value="minutes",key = "'project:'.concat(#p0.projectidx).concat(':minutes')")
    public List getMinutes(Project p) {
        return minuteRepository.findByProject(p);
    }

    @Override
    @CacheEvict(value="minutes",key="'project:'.concat(#p0.project.projectidx).concat(':minutes')")
    public Minute save(Minute m) {
        return minuteRepository.saveAndFlush(m);
    }

}

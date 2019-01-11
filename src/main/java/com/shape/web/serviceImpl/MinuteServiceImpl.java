package com.shape.web.serviceImpl;

import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import com.shape.web.repository.MinuteRepository;
import com.shape.web.service.MinuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public class MinuteServiceImpl implements MinuteService {

    private MinuteRepository minuteRepository;

    @Autowired
    public MinuteServiceImpl(MinuteRepository minuteRepository) {
        this.minuteRepository = minuteRepository;
    }

    @Override
    @Cacheable(value = "minutes", key = "'project:'.concat(#p0.projectidx).concat(':minutes')")
    public List<Minute> getMinutes(Project p) {
        return minuteRepository.findByProject(p);
    }

    @Override
    @Cacheable(value = "minute", key = "'project:'.concat(#p0.projectidx).concat(':minute:').concat(#p1)")
    public Minute getMinute(Project p, Date date) {
        return minuteRepository.findByProjectAndDate(p, date);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "minutes", key = "'project:'.concat(#p0.project.projectidx).concat(':minutes')"),
            @CacheEvict(value = "minute", key = "'project:'.concat(#p0.project.projectidx).concat(':minute:').concat(#p0.date)")
    })
    public Minute save(Minute m) {
        return minuteRepository.saveAndFlush(m);
    }

}

package com.shape.web.repository;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface AlarmRepository extends JpaRepository<Alarm,Integer> {

    List<Alarm> findByUser(User user);
    Alarm findFirstByContentidAndUserOrderByDateDesc(Integer contentid,User user); // 알람 하나


    List<Alarm> findByUserOrderByDateDesc(User user,Pageable pageable); // 타임 라인

    List<Alarm> findByUserAndContentidAndIsshowOrderByDateDesc(User user,Integer contentid,Boolean isshow); // 특정 종류 알람

    Alarm findFirstByUserAndActorAndContentidAndIsshowOrderByDateDesc(User user,User a,Integer contentid,Boolean isshow);

}

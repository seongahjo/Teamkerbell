package com.shape.web.repository;

import com.shape.web.entity.Alarm;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */
public interface AlarmRepository extends JpaRepository<Alarm,Integer> {
    List<Alarm> findByUser(User user);
    Alarm findFirstByContentidAndUserOrderByDateDesc(Integer contentid,User user);
    List<Alarm> findFirst15ByUserOrderByDateDesc(User user,Pageable pageable);
    List<Alarm> findByUserAndContentidAndIsshowOrderByDateDesc(User user,Integer contentid,Boolean isshow);

}

package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Alarm;
import com.sajo.teamkerbell.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    List<Alarm> findByUser(User user);

    Alarm findFirstByContentidAndUserOrderByDateDesc(Integer contentid, User user); // 알람 하나


    List<Alarm> findByUserOrderByDateDesc(User user, Pageable pageable); // 타임 라인

    List<Alarm> findByUserAndContentidAndIsshowOrderByDateDesc(User user, Integer contentid, Boolean isshow); // 특정 종류 알람

    Alarm findFirstByUserAndActorAndContentidAndIsshowOrderByDateDesc(User user, User a, Integer contentid, Boolean isshow);

}

package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by seongahjo on 2016. 7. 16..
 */

@Repository
public interface LogsRepository extends JpaRepository<Logs,Integer> {
    Logs findFirstByIpAndCreatedatBetweenOrderByCreatedat(String ip, Date start, Date end);
}

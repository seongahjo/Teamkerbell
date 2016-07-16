package com.shape.web.repository;

import com.shape.web.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
public interface LogRepository  extends JpaRepository<Log,Integer> {
    Log findFirstByIpAndCreatedatBetweenOrderByCreatedat(String ip,Date start,Date end);
}

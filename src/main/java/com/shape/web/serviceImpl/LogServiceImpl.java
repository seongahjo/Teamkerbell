package com.shape.web.serviceImpl;

import com.shape.web.entity.Logs;
import com.shape.web.entity.User;
import com.shape.web.repository.LogRepository;
import com.shape.web.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogRepository logRepositry;

    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
    @Override
    public void addLog(String ip,User u) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date time=null;
        try {
            time=simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.DATE,1);
            Date nextTime=simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
           Logs l = logRepositry.findFirstByIpAndCreatedatBetweenOrderByCreatedat(ip,time,nextTime);
           if (l == null) {
               l = new Logs(ip, u);
           }
           else{
               l.setUpdatedat(new Date());
           }
               logRepositry.saveAndFlush(l);

       }catch(ParseException e){

       }
    }
}

package com.shape.web.serviceImpl;

import com.shape.web.entity.Logs;
import com.shape.web.entity.User;
import com.shape.web.repository.LogsRepository;
import com.shape.web.service.LogsService;
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
public class LogsServiceImpl implements LogsService {
    @Autowired
    LogsRepository logsRepositry;

    private static final Logger logger = LoggerFactory.getLogger(LogsServiceImpl.class);
    @Override
    public void addLog(final String ip,final User u) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date time=null;
        try {
            time=simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.DATE,1);
            Date nextTime=simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
           Logs l = logsRepositry.findFirstByIpAndCreatedatBetweenOrderByCreatedat(ip,time,nextTime);
           if (l == null) {
               l = new Logs(ip, u);
           }
           else{
               l.setUpdatedat(new Date());
           }
              logsRepositry.saveAndFlush(l);
        }catch(ParseException e){

       }
    }
}

package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.Logs;
import com.sajo.teamkerbell.repository.LogsRepository;
import com.sajo.teamkerbell.service.LogsService;
import com.sajo.teamkerbell.entity.User;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LogsServiceImpl implements LogsService {

    private LogsRepository logsRepositry;

    @Autowired
    public LogsServiceImpl(LogsRepository logsRepositry) {
        this.logsRepositry = logsRepositry;
    }

    @Override
    public void addLog(final String ip, final String content, final User u) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        try {
            time = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.DATE, 1);
            Date nextTime = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
            Logs l = logsRepositry.findFirstByIpAndCreatedatBetweenOrderByCreatedat(ip, time, nextTime);
            if (l == null) {
                l = new Logs(ip, content, u);
            } else {
                l.setUpdatedat(new Date());
            }
            logsRepositry.saveAndFlush(l);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
    }
}

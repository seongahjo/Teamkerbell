package com.sajo.teamkerbell.aspect;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.entity.TodoList;
import com.sajo.teamkerbell.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TimelineAspect {

    private final TimelineService timelineService;

    @AfterReturning(value = "execution(* com.sajo.teamkerbell.service.*Service.save(..))", returning = "returnInstance")
    public void saveTimeline(JoinPoint jp, Object returnInstance) {
        if (returnInstance instanceof Minute) {
            Minute m = (Minute) returnInstance;
            timelineService.append(m);
        } else if (returnInstance instanceof Schedule) {
            Schedule s = (Schedule) returnInstance;
            timelineService.append(s);
        } else if (returnInstance instanceof FileDB) {
            FileDB f = (FileDB) returnInstance;
            timelineService.append(f);
        } else if (returnInstance instanceof TodoList) {
            TodoList t = (TodoList) returnInstance;
            timelineService.append(t);
        }

    }

}

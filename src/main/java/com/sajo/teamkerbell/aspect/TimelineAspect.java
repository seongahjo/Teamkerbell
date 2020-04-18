package com.sajo.teamkerbell.aspect;

import com.sajo.teamkerbell.entity.TimelineAdapter;
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
        if (returnInstance instanceof TimelineAdapter) {
            TimelineAdapter timeline = (TimelineAdapter) returnInstance;
            timelineService.append(timeline);
        }

    }

}

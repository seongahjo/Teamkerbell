package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.service.ScheduleService;
import com.sajo.teamkerbell.vo.ScheduleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by seongahjo on 2016. 2. 7..
 */

/*
 Handles requests for the calendar.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final ScheduleService scheduleService;


    @GetMapping(value = "/user/{userId}/schedule")
    public ResponseEntity<List<Schedule>> getSchedulesByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(scheduleService.getScheduleByUserId(userId, page, size));
    }

    /*
   To make schedule
   */
    @PostMapping(value = "/project/{projectId}/schedule")
    public ResponseEntity<Schedule> makeSchedule(
            @PathVariable("projectId") Integer projectId,
            @RequestBody @Valid ScheduleVO scheduleVO,
            BindingResult result) {
        if (result.hasErrors()) throw new IllegalArgumentException();
        Schedule schedule = scheduleService.save(projectId, scheduleVO);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping(value = "/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable("scheduleId") Integer scheduleId,
            @RequestBody @Valid ScheduleVO scheduleVO,
            BindingResult result) {
        if (result.hasErrors()) throw new IllegalArgumentException();
        scheduleService.update(scheduleId, scheduleVO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user/{userId}/schedule/{scheduleId}")
    public ResponseEntity<Void> createAppointment(
            @PathVariable("userId") Integer userId,
            @PathVariable("scheduleId") Integer scheduleId) {
        scheduleService.assignAppointment(scheduleId, userId);
        return ResponseEntity.ok().build();
    }

}

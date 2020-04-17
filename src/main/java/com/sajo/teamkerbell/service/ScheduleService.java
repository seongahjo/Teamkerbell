package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Appointment;
import com.sajo.teamkerbell.entity.Schedule;
import com.sajo.teamkerbell.repository.ScheduleRepository;
import com.sajo.teamkerbell.vo.ScheduleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getScheduleByUserId(Integer userId, int page, int size) {
        return scheduleRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    public Schedule save(Integer projectId, ScheduleVO scheduleVO) {
        return scheduleRepository.save(Schedule.from(projectId, scheduleVO));
    }

    public Schedule update(Integer scheduleId, ScheduleVO scheduleVO) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalStateException::new);
        schedule.update(scheduleVO);
        return schedule;
    }

    public Schedule assignAppointment(Integer scheduleId, Integer userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalStateException::new);
        boolean isDuplicated = schedule.getAppointments().stream().anyMatch(a -> a.getUserId().equals(userId));
        if (isDuplicated) throw new IllegalArgumentException();
        Appointment appointment = new Appointment(userId);
        appointment.assignTo(schedule);
        return schedule;
    }

}

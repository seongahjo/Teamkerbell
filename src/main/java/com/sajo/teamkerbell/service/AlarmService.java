package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.Alarm;
import com.sajo.teamkerbell.repository.AlarmRepository;
import com.sajo.teamkerbell.vo.AlarmVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public Alarm invite(AlarmVO alarmVO) {
        Alarm alarm = Alarm.from(alarmVO);
        return alarmRepository.save(alarm);
    }

    public List<Alarm> getAlarmsByInviteeId(Integer inviteeId, int page, int size) {
        return alarmRepository.findByInviteeId(inviteeId, PageRequest.of(page, size));
    }
}

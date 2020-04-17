package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.Alarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    List<Alarm> findByInviteeId(Integer inviteeId, Pageable pageable);
}

package com.sajo.teamkerbell.VO;

import lombok.Data;

import java.util.Date;

/**
 * Created by seongahjo on 2016. 2. 7..
 */
@Data
public class MeetingMember {
    private Integer scheduleidx;
    private Date date;
    private String participant;
    private String nonparticipant;
    private String place;
    private String content;

    public MeetingMember() {
    }

    public MeetingMember(Integer scheduleidx, Date date, String participant, String nonparticipant, String place, String content) {
        this.scheduleidx = scheduleidx;
        this.date = date;
        this.participant = participant;
        this.nonparticipant = nonparticipant;
        this.place = place;
        this.content = content;
    }
}

package com.shape.web.VO;

import java.util.Date;

/**
 * Created by seongahjo on 2016. 2. 7..
 */
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

    public Integer getScheduleidx() {
        return scheduleidx;
    }

    public void setScheduleidx(Integer scheduleidx) {
        this.scheduleidx = scheduleidx;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getNonparticipant() {
        return nonparticipant;
    }

    public void setNonparticipant(String nonparticipant) {
        this.nonparticipant = nonparticipant;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

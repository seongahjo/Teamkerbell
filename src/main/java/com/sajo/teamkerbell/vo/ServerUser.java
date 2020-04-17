package com.sajo.teamkerbell.vo;


import lombok.Data;

@Data
public class ServerUser {
    private String projectIdx;
    private Integer userIdx;
    private String id;
    private String name;
    private String img;
    private String socketId;

    public ServerUser() {
    }

    public ServerUser(String projectIdx, Integer userIdx, String id, String name, String img, String socketId) {
        this.projectIdx = projectIdx;
        this.userIdx = userIdx;
        this.id = id;
        this.name = name;
        this.img = img;
        this.socketId = socketId;
    }

}

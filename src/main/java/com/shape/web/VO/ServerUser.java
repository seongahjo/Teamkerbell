package com.shape.web.VO;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectIdx() {
        return projectIdx;
    }

    public void setProjectIdx(String projectIdx) {
        this.projectIdx = projectIdx;
    }

    public Integer getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(Integer userIdx) {
        this.userIdx = userIdx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}

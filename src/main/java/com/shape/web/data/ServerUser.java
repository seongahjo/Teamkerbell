package com.shape.web.data;

public class ServerUser {
    private String projectIdx;
    private Integer userIdx;
    private String name;
    private String img;

    public ServerUser() {
    }

    public ServerUser(String projectIdx, Integer userIdx, String name, String img) {
        this.projectIdx = projectIdx;
        this.userIdx = userIdx;
        this.name = name;
        this.img = img;
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
}

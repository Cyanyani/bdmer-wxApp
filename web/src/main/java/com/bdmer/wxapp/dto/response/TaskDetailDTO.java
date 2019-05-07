package com.bdmer.wxapp.dto.response;

/**
 * 任务详情 - DTO类
 *
 * @since 2019-05-07
 * @author gongdl
 */
public class TaskDetailDTO {
    private Long uid;
    private String name;
    private String avatarUrl;

    private String title;
    private String content;
    private String[] pictrues;


    private String createTime;
    private String endTime;
    private String localeName;
    private String telNumber;
    private String type;
    private String status;

    private Integer reward;
    private Integer givePoint;
    private Long doUid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getPictrues() {
        return pictrues;
    }

    public void setPictrues(String[] pictrues) {
        this.pictrues = pictrues;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(Integer givePoint) {
        this.givePoint = givePoint;
    }

    public Long getDoUid() {
        return doUid;
    }

    public void setDoUid(Long doUid) {
        this.doUid = doUid;
    }
}

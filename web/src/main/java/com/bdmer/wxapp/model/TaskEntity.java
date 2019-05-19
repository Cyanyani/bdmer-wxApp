package com.bdmer.wxapp.model;

public class TaskEntity {
    private Long id;

    private Long uid;

    private String name;

    private String avatarUrl;

    private String title;

    private String content;

    private String pictrues;

    private Long creatstamp;

    private Long endstamp;

    private Double lat;

    private Double lng;

    private String localeName;

    private String telNumber;

    private Integer reward;

    private Long douid;

    private String status;

    private String type;

    private Integer givePoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPictrues() {
        return pictrues;
    }

    public void setPictrues(String pictrues) {
        this.pictrues = pictrues;
    }

    public Long getCreatstamp() {
        return creatstamp;
    }

    public void setCreatstamp(Long creatstamp) {
        this.creatstamp = creatstamp;
    }

    public Long getEndstamp() {
        return endstamp;
    }

    public void setEndstamp(Long endstamp) {
        this.endstamp = endstamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Long getDouid() {
        return douid;
    }

    public void setDouid(Long douid) {
        this.douid = douid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(Integer givePoint) {
        this.givePoint = givePoint;
    }
}
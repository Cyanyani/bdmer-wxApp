package com.bdmer.wxapp.model;

public class RecordEntity {
    private Long id;

    private Long uid;

    private String event;

    private String eventdetail;

    private Integer getpoint;

    private String createtime;

    private Long createstamp;

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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventdetail() {
        return eventdetail;
    }

    public void setEventdetail(String eventdetail) {
        this.eventdetail = eventdetail;
    }

    public Integer getGetpoint() {
        return getpoint;
    }

    public void setGetpoint(Integer getpoint) {
        this.getpoint = getpoint;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Long getCreatestamp() {
        return createstamp;
    }

    public void setCreatestamp(Long createstamp) {
        this.createstamp = createstamp;
    }
}
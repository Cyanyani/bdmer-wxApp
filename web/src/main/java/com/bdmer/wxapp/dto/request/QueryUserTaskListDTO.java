package com.bdmer.wxapp.dto.request;

/**
 * 查询用户任务 - DTO类
 *
 * @since 2019-05-08
 * @author gongdl
 */
public class QueryUserTaskListDTO {
    private Integer index;
    private Integer size;
    private String myType;
    private String myStatus;
    private Double lat;
    private Double lng;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String myType) {
        this.myType = myType;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
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
}

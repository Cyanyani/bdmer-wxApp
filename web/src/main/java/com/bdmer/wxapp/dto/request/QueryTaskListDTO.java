package com.bdmer.wxapp.dto.request;

/**
 * 查询任务列表
 *
 * @since 2019-05-07
 * @author gongdl
 */
public class QueryTaskListDTO {
    private Integer index;
    private Integer size;
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

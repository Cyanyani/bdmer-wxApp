package com.bdmer.wxapp.dto.request;

/**
 * 位置信息 - DTO类
 *
 * @since 2019-05-04
 * @author gongdl
 */
public class LocaleDTO {
    private String name;
    private String address;
    private String latitude;
    private String longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

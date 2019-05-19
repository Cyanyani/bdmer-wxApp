package com.bdmer.wxapp.dto.response;

/**
 * 执行人 - DTO类
 *
 * @since 2019-05-07
 * @author gongdl
 */
public class DoUserDTO {
    private String name;
    private String avatarUrl;
    private String telNumber;

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

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumbre) {
        this.telNumber = telNumbre;
    }
}

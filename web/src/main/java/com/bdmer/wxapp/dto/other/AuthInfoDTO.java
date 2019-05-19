package com.bdmer.wxapp.dto.other;

/**
 * bdmer用户认证信息 - DTO类
 *
 * @since 2019-05-05
 * @author gongdl
 */
public class AuthInfoDTO {

    private String unionid;

    private String authImage;

    private Integer authStatus;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAuthImage() {
        return authImage;
    }

    public void setAuthImage(String authImage) {
        this.authImage = authImage;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }
}

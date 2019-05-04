package com.bdmer.wxapp.dto.other;

/**
 * 用户token - DTO类
 *
 * @since 2019-05-02
 * @author gongdl
 */
public class UserTokenDTO {
    private String sessionKey = "";
    private String openid = "";
    private String unionid = "";

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}

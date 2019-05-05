package com.bdmer.wxapp.common.tool;

import com.bdmer.wxapp.dto.other.UserTokenDTO;

/**
 * 微信用户信息接收器 - 工具类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public class WxUserHolder {

    private static String token = "";

    private static UserTokenDTO userTokenDTO = new UserTokenDTO();

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        WxUserHolder.token = token;
    }

    public static UserTokenDTO getUserTokenDTO() {
        return userTokenDTO;
    }

    public static void setUserTokenDTO(UserTokenDTO userTokenDTO) {
        WxUserHolder.userTokenDTO = userTokenDTO;
    }

    public static String getSessionKey() {
        return userTokenDTO.getSessionKey();
    }

    public static void setSessionKey(String sessionKey) {
        userTokenDTO.setSessionKey(sessionKey);
    }

    public static String getOpenid() {
        return userTokenDTO.getOpenid();
    }

    public static void setOpenid(String openid) {
        userTokenDTO.setOpenid(openid);
    }

    public static String getUnionid() {
        return userTokenDTO.getUnionid();
    }

    public static void setUnionid(String unionid) {
        userTokenDTO.setUnionid(unionid);
    }
}

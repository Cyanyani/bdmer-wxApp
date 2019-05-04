package com.bdmer.wxapp.common.enums;

/**
 * 返回结果（是层与层之间的通信标准） - 枚举类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public enum ResponseEnum {
    SUCCESS(0, "成功"),

    /** 未知错误 **/
    ERROR(-1, "未知错误"),

    /** 请求错误 **/
    ERROR_REQ_NO_PARAM(1001, "请求参数为空"),
    ERROR_REQ_NO_NET(1002, "没有网络"),

    /** 微信用户错误 **/
    ERROR_WX_USER_CODE(2001, "错误code"),
    ERROR_WX_USER_INFO(2002, "错误用户信息,解密失败"),
    ERROR_WX_USER_TOKEN(2003, "错误token"),
    ERROR_WX_USER_NO_TOKEN(2004, "token已过期"),
    ERROR_WX_USER_TOKEN_NEED_INFO(2005, "token需要用户信息");

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /** 状态码 **/
    private Integer code;

    /** 消息 **/
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

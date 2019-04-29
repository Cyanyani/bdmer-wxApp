package com.bdmer.wxapp.common.enums;

public enum ResponseEnum {
    SUCCESS(0, "成功"),

    /** 系统错误 **/
    ERROR(-1, "未知错误"),

    /** 用户错误 **/
    ERROR_USER_CODE(10001, "错误code"),
    ERROR_USER_INFO(10002, "错误用户信息,解密失败"),
    ERROR_USER_TOKEN(10003, "错误token"),
    ERROR_USER_NO_TOKEN(10004, "token已过期"),
    ERROR_USER_TOKEN_NEED_INFO(10005, "token需要用户信息");

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

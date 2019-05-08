package com.bdmer.wxapp.common.enums;

/**
 * 用户任务类型 - 枚举类
 *
 * @sicne 2019-05-08
 * @suthor gongdl
 */
public enum UserTaskTypeEnum {
    USER_TASK_TYPE_ENUM_PUBLISH("PUBLISH", "用户发布的"),
    USER_TASK_TYPE_ENUM_RECEIVE("RECEIVE", "用户领取的");

    UserTaskTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /** 状态码 **/
    private String code;

    /** 消息 **/
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}

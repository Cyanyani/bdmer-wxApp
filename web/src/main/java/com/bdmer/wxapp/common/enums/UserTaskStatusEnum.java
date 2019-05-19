package com.bdmer.wxapp.common.enums;

/**
 * 用户任务状态 - 枚举类
 *
 * @sicne 2019-05-08
 * @suthor gongdl
 */
public enum UserTaskStatusEnum {
    USER_TASK_STATUS_ENUM_CURRENT("CURRENT", "当前任务"),
    USER_TASK_STATUS_ENUM_HISTORY("HISTORY", "历史任务");

    UserTaskStatusEnum(String code, String msg) {
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

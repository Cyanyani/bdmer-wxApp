package com.bdmer.wxapp.common.enums;

/**
 * 任务状态枚举
 *
 * @since 2019-05-06
 * @author gongdl
 */
public enum TaskStatusEnum {
    /**
     * 当前任务
     */
    STATUS_IS_FINDING("FINDING", "发布中"),
    STATUS_IS_DOING("DOING", "执行中"),

    /**
     * 历史任务
     */
    STATUS_IS_CANCEL("CANCEL", "已取消"),
    STATUS_IS_FINISH("FINISH", "已完成");

    TaskStatusEnum(String code, String msg) {
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

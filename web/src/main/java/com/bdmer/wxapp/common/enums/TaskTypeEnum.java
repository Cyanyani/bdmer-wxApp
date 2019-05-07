package com.bdmer.wxapp.common.enums;

/**
 * 任务类型枚举
 *
 * @since 2019-05-06
 * @author gongdl
 */
public enum TaskTypeEnum {
    /**
     * 当前任务
     */
    TYPE_IS_NOTICE("NOTICE", "通知公告"),
    TYPE_IS_WAIKUAI("WAIKUAI", "外卖快递"),

    /**
     * 历史任务
     */
    TYPE_IS_LOSTF("LOSTF", "失物招领"),
    TYPE_IS_OTHER("OTHER", "其他");

    TaskTypeEnum(String code, String msg) {
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

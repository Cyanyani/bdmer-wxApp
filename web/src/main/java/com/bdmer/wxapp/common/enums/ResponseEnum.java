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
    ERROR_WX_USER_TOKEN_NEED_INFO(2005, "token需要用户信息"),
    ERROR_WX_NO_USER(2006, "用户不存在-wx"),

    /** bdmer用户错误 **/
    ERROR_BDMER_NO_USER(3000, "用户不存在"),
    ERROR_BDMER_USER_TEL(3002, "加密的手机号码有误"),
    ERROR_BDMER_IMG_FORMAT(3003, "图片格式不正确"),

    /** 任务相关错误 **/
    ERROR_TASK_NO_TASK(4000, "任务不存在"),
    ERROR_TASK_NO_STATUS(4001, "不存在该状态"),
    ERROR_TASK_CANNT_CANCEL(4002, "不能取消，该任务不属于该用户"),
    ERROR_TASK_CANNT_GIVE_POINT(4003, "不能评分，该任务不属于该用户"),
    ERROR_TASK_ERROR_QUERY_PARAMS(4003, "错误的查询参数"),
    ERROR_TASK_ERROR_POINT(4004, "评论分数不正确"),
    ERROR_TASK_NO_PASS_AUTH(4005, "请先完成身份认证"),
    ERROR_TASK_NO_TEL(4006, "请先绑定手机号码");

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

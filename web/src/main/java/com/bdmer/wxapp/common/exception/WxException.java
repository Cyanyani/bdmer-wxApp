package com.bdmer.wxapp.common.exception;

import com.bdmer.wxapp.common.enums.ResponseEnum;

/**
 * 微信小程序相关 - 异常类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public class WxException extends Exception{
    private ResponseEnum responseEnum;

    public WxException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResultEnum() {
        return responseEnum;
    }

    public void setResultEnum(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
}

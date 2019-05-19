package com.bdmer.wxapp.common.exception;

import com.bdmer.wxapp.common.enums.ResponseEnum;

/**
 * 网站相关异常 - 异常类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public class WebException extends Exception {
    private ResponseEnum responseEnum;

    public WebException(ResponseEnum responseEnum) {
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

package com.bdmer.wxapp.common.tool;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.dto.response.ResponseDTO;

/**
 * 结果返回 - 工具类
 *
 * @since 2019-04-29
 * @author gongdl
 */
public class B {
    /**
     * 成功 - 返回结果
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ResponseDTO<T> success(T object) {
        ResponseDTO<T> result = new ResponseDTO<>();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMsg(ResponseEnum.SUCCESS.getMsg());
        result.setData(object);

        return result;
    }

    /**
     * 失败 - 返回结果
     *
     * @param responseEnum
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ResponseDTO<T> error(ResponseEnum responseEnum, T object) {
        ResponseDTO<T> result = new ResponseDTO<>();
        result.setCode(responseEnum.getCode());
        result.setMsg(responseEnum.getMsg());
        result.setData(object);

        return result;
    }
}

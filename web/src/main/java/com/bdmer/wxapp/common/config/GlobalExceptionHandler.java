package com.bdmer.wxapp.common.config;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉（Controller层）
 * @since 2019-04-29
 * @author gongdl
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO<?> exceptionHandler(Exception e){
        LOG.error("[系统异常]:", e.getMessage());
        return B.error(ResponseEnum.ERROR, e.getMessage());

    }
}

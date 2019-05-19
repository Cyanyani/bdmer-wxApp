package com.bdmer.wxapp.common.config;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.exception.WebException;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉（Controller层）- 异常捕捉类
 *
 * @since 2019-04-29
 * @author gongdl
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO<?> exceptionHandler(Exception e){
        if (e instanceof WxException) {
            LOG.error("【wx相关异常】 - {}", e.getMessage());
            WxException wxException = (WxException) e;

            return B.error(wxException.getResultEnum(), null);

        } else if (e instanceof WebException) {
            LOG.error("【web相关异常】 - {}", e.getMessage());
            WebException webException = (WebException) e;

            return B.error(webException.getResultEnum(), null);

        } else {
            LOG.error("【系统相关异常】 - {}", e.getMessage());

            return B.error(ResponseEnum.ERROR, e.getMessage());

        }
    }
}

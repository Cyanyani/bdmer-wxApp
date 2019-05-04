package com.bdmer.wxapp.common.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间相关 - 工具类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public class TimeUtil {
    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static Long getTimeStamp(){
        return new Date().getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTimeFormat(){
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}

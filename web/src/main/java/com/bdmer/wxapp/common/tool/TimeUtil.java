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

    /**
     * 时间转时间戳
     *
     * @param pattern
     * @param time
     * @return
     * @throws Exception
     */
    public static Long getTimeStampByFormat(String pattern, String time) throws Exception{

        //注意format的格式要与日期String的格式相匹配
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(time);

        return  date.getTime();
    }

    public static String timeStampToString(Long timeStamp) throws Exception{

        //注意format的格式要与日期String的格式相匹配
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  sdf.format(new Date(timeStamp));
    }

}

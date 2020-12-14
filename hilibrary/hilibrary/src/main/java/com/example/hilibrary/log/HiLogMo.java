package com.example.hilibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/***
 * 日志打印的模型
 */
public class HiLogMo {
    // 时间格式化对象
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd:HH:mm:ss", Locale.CHINA);
    // 日志打印的时间戳
    public long timeMillis;
    // 日志打印的级别
    public int level;
    // 日志打印的tag
    public String tag;
    // 日志打印的内容
    public String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    /***
     * 格式化日志信息
     * @return
     */
    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    /***
     * 格式化除日志内容外的信息
     * @return
     */
    public String getFlattened() {
        return format(timeMillis) + "|" + level + "|" + tag + "|:";
    }

    /***
     * 格式化时间戳
     * @return
     */
    public String format(long timeMillis) {
        return simpleDateFormat.format(timeMillis);
    }

}

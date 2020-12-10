package com.example.hilibrary.log;

import org.jetbrains.annotations.NotNull;

/***
 * @date 2020年12月10日
 * @author 栾桂明
 * @desc  日志打印的接口
 */
public interface HiLogPrinter {
    /****
     * @param config  日志的配置信息
     * @param level   日志的级别
     * @param tag     日志的标识
     * @param printString  打印的日志信息
     */
    void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString);
}

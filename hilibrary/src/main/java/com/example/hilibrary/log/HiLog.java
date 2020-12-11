package com.example.hilibrary.log;

import android.util.Log;

import com.example.hilibrary.log.printer.HiLogPrinter;
import com.example.hilibrary.log.utils.HiStackTraceUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/***
 * 日志打印框架的核心
 *   1 打印堆栈信息
 *   2 File输出信息
 *   3 模拟控制台
 */
public class HiLog {
    //忽律的日志信息的包名
    private static final String HI_LOG_PACKAGE;

    //获取当前的Library的包名
    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    /***
     * 打印信息
     * @param contents 打印的内容
     */
    public static void v(Object... contents) {
        log(HiLogType.V, contents);
    }

    /***
     * 打印信息
     * @param tag 打印的标识
     * @param contents 打印的内容
     */
    public static void vt(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }

    /**
     * 打印信息
     *
     * @param contents 打印的内容
     */
    public static void d(Object... contents) {
        log(HiLogType.D, contents);
    }

    /***
     * 打印信息
     * @param tag 打印的标识
     * @param contents 打印的内容
     */
    public static void dt(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }


    public static void i(Object... contents) {
        log(HiLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
    }


    public static void e(Object... contents) {
        log(HiLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);
    }

    public static void w(Object... contents) {
        log(HiLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(HiLogType.W, tag, contents);
    }

    public static void a(Object... contents) {
        log(HiLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }

    /****
     * 日志打印方法
     * @param type  日志打印的类型
     * @param contents  打印的日志的内容
     */
    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    /***
     * 日志打印方法
     * @param type 日志打印的类型
     * @param tag  日志打印的标识
     * @param contents  打印的日志的内容
     */
    public static void log(@HiLogType.TYPE int type, @NotNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);

    }

    /***
     * 日志打印方法
     * @param config 日志打印的配置信息
     * @param type   日志打印的类型
     * @param tag    日志打印的标识
     * @param contents   打印的日志的内容
     */
    public static void log(@NotNull HiLogConfig config, @HiLogType.TYPE int type, @NotNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            builder.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTrace = HiLogConfig.HI_STACK_TRACE_FORMATTER.format(HiStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), HI_LOG_PACKAGE, config.stackTraceDepth()));
            builder.append(stackTrace).append("\n");

        }
        String body = parseBody(contents, config);
        builder.append(body);
        List<HiLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //打印Log
        for (HiLogPrinter printer : printers) {
            printer.print(config, type, tag, builder.toString());
        }
    }

    private static String parseBody(@NotNull Object[] contents, @NotNull HiLogConfig config) {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();


    }
}

package com.example.hi_library.log.common;



import com.example.hi_library.log.printer.HiLogPrinter;
import com.example.hi_library.utils.HiStackTraceUtil;

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
        //判断是包含线程信息
        if (config.includeThread()) {
            //格式化线程信息
            String threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            //拼接格式化的信息
            builder.append(threadInfo).append("\n");
        }
        //判断是否包含堆栈信息
        if (config.stackTraceDepth() > 0) {
            //格式化堆栈信息
            String stackTrace = HiLogConfig.HI_STACK_TRACE_FORMATTER.format(HiStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), HI_LOG_PACKAGE, config.stackTraceDepth()));
            //拼接格式化信息
            builder.append(stackTrace).append("\n");

        }
        //解析日志数据对象
        String body = parseBody(contents, config);
        //拼接log日志信息
        builder.append(body);
        //
        List<HiLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //打印Log
        for (HiLogPrinter printer : printers) {
            printer.print(config, type, tag, builder.toString());
        }
    }

    /****
     * 解析Log日志的数据对象
     * @param contents   等待解析的日志对象
     * @param config  日志解析配置对象
     * @return
     */
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

package com.example.hilibrary.log;

import com.example.hilibrary.log.formatter.HiStackTraceFormatter;
import com.example.hilibrary.log.formatter.HiThreadFormatter;
import com.example.hilibrary.log.printer.HiLogPrinter;

/****
 * 日志打印的配置类
 */
public abstract class HiLogConfig {
    public static int MAX_LEN = 512;
    //懒汉模式的单利创建 日志格式化器
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();

    /***
     * 返回一个全局的日志打印的tag
     * @return
     */
    public String getGlobalTag() {
        return "HiLog";
    }

    public JsonParser injectJsonParser() {
        return null;
    }

    /***
     *
     * @return
     */
    public boolean enable() {
        return true;
    }

    /***
     * 堆栈中是否包含线程信息
     * @return
     */
    public boolean includeThread() {
        return false;
    }

    /***
     * 控制堆栈信息的深度
     * @return
     */
    public int stackTraceDepth() {
        return 5;
    }

    /***
     * 日志打印器的配置
     * @return
     */
    public HiLogPrinter[] printers() {
        return null;
    }

    /***
     * 对象的序列化
     */
    public interface JsonParser {
        String toJson(Object src);


    }
}

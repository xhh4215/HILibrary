package com.example.hilibrary.log;

/****
 * 日志打印的配置类
 */
public abstract class HiLogConfig {
    /***
     * 返回一个全局的日志打印的tag
     * @return
     */
    public String getGlobalTag() {
        return "HiLog";
    }

    /***
     *
     * @return
     */
    public boolean enable() {
        return true;
    }
}

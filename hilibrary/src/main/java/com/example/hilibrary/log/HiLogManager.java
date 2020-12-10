package com.example.hilibrary.log;

import androidx.annotation.NonNull;

/***
 * 日志打印的管理对象
 */
public class HiLogManager {
    //日志打印配置对象
    private HiLogConfig config;
    //单例
    private static HiLogManager instance;

    /***
     * 私有的构造器
     * @param config
     */
    private HiLogManager(HiLogConfig config) {
        this.config = config;

    }

    /***
     * 获取静态实例
     * @return
     */
    public static HiLogManager getInstance() {
        return instance;
    }

    /***
     * 初始化静态实例对象
     * @param config
     */
    public static void init(@NonNull HiLogConfig config) {
        instance = new HiLogManager(config);
    }

    /***
     * 获取日志打印的配置对象
     * @return
     */
    public HiLogConfig getConfig() {
        return config;
    }
}

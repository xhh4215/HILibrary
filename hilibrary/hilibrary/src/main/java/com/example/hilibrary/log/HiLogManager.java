package com.example.hilibrary.log;

import androidx.annotation.NonNull;

import com.example.hilibrary.log.printer.HiLogPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 日志打印的管理对象
 */
public class HiLogManager {
    //日志打印配置对象
    private HiLogConfig config;
    //单例
    private static HiLogManager instance;
    //配置打印器
    private List<HiLogPrinter> printers = new ArrayList<>();

    /***
     * 私有的构造器
     * @param config
     */
    private HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        //此处展示了如何将数组添加到List中
        this.printers.addAll(Arrays.asList(printers));

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
    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printer) {
        instance = new HiLogManager(config, printer);
    }

    /***
     * 获取日志打印的配置对象
     * @return
     */
    public HiLogConfig getConfig() {
        return config;
    }

    /***
     * 获取配置的所有的打印器
     * @return
     */
    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    /***
     * 添加一个打印器
     * @param printer
     */
    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    /***
     * 移除一个日志打印器
     * @param printer
     */
    public void removePrinter(HiLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }
}

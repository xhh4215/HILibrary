package com.example.common.unuse.singledata.java;

/***
 * java的饿汉模式的单例
 *  实现的思想：创建同时初始化一个静态的变量 ，  提供一个静态的方法来获取这个变量
 * 1 在类创建的时候就在内存创建了对象，利用空间换时间
 * 2 它不存在线程安全的问题
 * 3 他在对象不用的使用也是存在的，会比较占内存
 */
public class SingleInstanceInJava {
    private static SingleInstanceInJava instance = new SingleInstanceInJava();

    public static SingleInstanceInJava getInstance() {
        return instance;
    }
}

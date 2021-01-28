package com.example.common.unuse.singledata.java;

/***
 * java单例的懒汉模式
 * 实现的思想 声明一个静态的变量 同时将构造方法私有化，在通过静态的方法初始化
 * 实例的时候使用 synchronized 方法
 * 1 利用时间换空间
 *
 */
public class SingleInstanceTInJava {
    private static SingleInstanceTInJava instance;

    private SingleInstanceTInJava() {

    }

    public static synchronized SingleInstanceTInJava getInstance() {
        if (instance == null) {
            instance = new SingleInstanceTInJava();
        }
        return instance;
    }
}

package com.example.common.unuse.singledata.java;

/***
 * java的双重校验锁的单例
 * 实现的思想   在非线程安全的状态下要判断instance的null性 避免多次示例化
 *             在使用了synchronized 关键字内部判断null 避免多个线程多次示例化
 */
public class SingleInstanceDInJava {

    private volatile static SingleInstanceDInJava instance;

    private SingleInstanceDInJava() {

    }

    public static SingleInstanceDInJava getInstance() {
        //避免Instance非空的时候进入同步代码块
        if (instance == null) {
            synchronized (SingleInstanceDInJava.class) {
                // 避免两个线程同时访问
                if (instance == null) {
                    instance = new SingleInstanceDInJava();
                }

            }
        }
        return instance;
    }
}

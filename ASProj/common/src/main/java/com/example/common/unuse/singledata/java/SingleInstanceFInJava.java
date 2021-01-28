package com.example.common.unuse.singledata.java;

/***
 * java通过静态内部类的形式实现单例
 * 实现的思想：提供一个私有化静态的内部类进行实例的初始化
 * 私有化构造器
 * 通过一个一个静态方法 使用静态内部类获取对象
 */
public class SingleInstanceFInJava {
    private static class SingleTonProvider {
        private static SingleInstanceFInJava instance = new SingleInstanceFInJava();
    }

    private SingleInstanceFInJava() {

    }

    private static SingleInstanceFInJava getInstance() {
        return SingleTonProvider.instance;
    }

}




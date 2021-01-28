package com.example.handlerproject.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/***
 * 使用原子类处理多线程的并发问题
 */
public class AtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        final AtomicTask task = new AtomicTask();
        Runnable runnable = () -> {
            for (int i = 0; i < 10000; i++) {
                task.incrementAtomic();
                task.incrementVolatile();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        // join 插入到当前的线程队列中  等待目标线程执行结束在执行当前的线程
        thread1.join();
        thread2.join();
        System.out.println("原子类的结果：" + task.atomicInteger.get());
        System.out.println("volatile修饰的结果：" + task.volatileCount);
    }


    static class AtomicTask {
        // 整形的原子类
        AtomicInteger atomicInteger = new AtomicInteger();

        volatile int volatileCount = 0;

        void incrementAtomic() {
            //原子类数值加一
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile() {
            volatileCount++;
        }
    }
}

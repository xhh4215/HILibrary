package com.example.hithrad;

import android.util.Log;

public class ThreadLifeCycleTest {

    static volatile boolean hasNotify = false;

    /***
     * wait：释放资源锁  进入等待线程池
     * notify 适用于线程同步 一个线程需要等待另外一个线程的执行结果或者是部分结果
     */
    public static void testWait() {
        Object object = new Object();


        class Runnable1 implements Runnable {
            @Override
            public void run() {
                Log.e("ThreadLifeCycleTest", "run thread1 start");
                // object 即资源对象  只有获取到了资源对象锁才有机会执行下边的代码
                synchronized (object) {
                    try {
                        //释放资源锁
                        if (hasNotify) {
                            object.wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("ThreadLifeCycleTest", "run :thread1  end");
            }
        }

        class Runnable2 implements Runnable {
            @Override
            public void run() {
                Log.e("ThreadLifeCycleTest", "run thread2 start");
                // object 即资源对象  只有获取到了资源对象锁才有机会执行下边的代码
                synchronized (object) {
                    //唤醒等待状态下的线程  但是并不会释放资源锁
                    object.notify();
                    hasNotify = true;
                }
                Log.e("ThreadLifeCycleTest", "run :thread2 end");
            }
        }
        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();


    }

    /***
     * join 的使用场景是一个线程需要等待另外一个线程执行完成才能继续执行的场景
     * join  理解为插入
     */
    public static void testJoin() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("ThreadLifeCycleTest", "run 1" + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("ThreadLifeCycleTest", "run 2" + System.currentTimeMillis());

            }
        });
        thread.start();
        try {
            //此处理解为thread调用了join方法插入到主线程之前执行在他执行结束之后主线程才能执行
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("ThreadLifeCycleTest", "test" + System.currentTimeMillis());

    }


}


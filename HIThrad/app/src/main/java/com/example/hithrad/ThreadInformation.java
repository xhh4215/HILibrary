package com.example.hithrad;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class ThreadInformation {

    public static void testInformation() {

        class LooperThread extends Thread {
            private Looper looper;

            public Looper getLooper() {
                synchronized (this) {
                    if (looper == null && isAlive()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return looper;
            }

            @Override
            public void run() {
                // Initialize the current thread as a looper.
                Looper.prepare();
                synchronized (this) {
                    //Return the Looper object associated with the current thread
                    looper = Looper.myLooper();
                    notify();
                }
                //Run the message queue in this thread. Be sure to call message queue to end the loop.
                Looper.loop();
            }
        }
        LooperThread looperThread = new LooperThread();
        looperThread.start();
        //关联自线程的looper创建一个handler对象
        Handler handler = new Handler(looperThread.getLooper()) {
            //在子线程中进行数据的处理
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e("handler", "handleMessage" + msg.what);
                Log.e("handler", "handleMessage" + Thread.currentThread().getName());
            }
        };
        // 在主线程发送消息
        handler.sendEmptyMessage(12);
    }
}

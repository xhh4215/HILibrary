package com.example.hithrad

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log

/***
 * 1 HandlerThread 使用于主线程和子线程通讯的场景
 * 2 用于持续性任务
 */
class HandlerThreadTest {
    companion object {
        fun test() {
            //创建一个HandlerThread对象  内部创建了一个Looper对象 可以轮询自己线程消息
            val thread = HandlerThread("handlerthread")
            //开启线程
            thread.start()
            //将HandlerThread的Looper和Handler绑定  进而处理消息
            val handler = object :ThreadHandler(thread.looper){
                override fun handleMessage(msg: Message) {
                   when(msg.what){
                       1->{
                           Log.e("tag","handler ")
                       }
                   }

                }
            }
            handler.sendEmptyMessage(1)
            //停止HandlerThread
            thread.quitSafely()

        }
        //防止内存泄漏
        open class ThreadHandler(looper: Looper) : Handler()
    }


}
package com.example.hi_library.executor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/***
 *  支持按任务的优先级执行  支持线程池的暂停和恢复，异步结果主动回调到主线程
 *
 */
object HiExecutor {
    private const val TAG = "HiExecutor"
    private var hiExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock
    private var pasueCondition: Condition
    private var isPaused: Boolean = false
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        lock = ReentrantLock()
        pasueCondition = lock.newCondition()
        //cpu核数
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //核心线程数
        val corePoolSize = cpuCount + 1
        //最大线程数
        val maxPoolSize = cpuCount * 2 + 1
        //blockQueue
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()

        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS
        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "hi-executor-${seq.getAndIncrement()}"
            return@ThreadFactory thread
        }


        hiExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            /***
             * 在线程池内部的线程开始执行之前调用的方法
             */
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    lock.lock()
                    try {
                        pasueCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务，线程创建数量，正在运行的数量
                Log.d(TAG, "已经执行完的任务的优先级是：${(r as PriorityRunnable).priority}")
            }
        }
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    abstract class Callable<T> : Runnable {
        override fun run() {
            /***
             * Causes the Runnable r to be added to the message queue.
             * The runnable will be run on the thread to which this handler is
             * attached.
             */
            mainHandler.post {
                onPrepare()
            }
            val t = onBackground()
            mainHandler.post {
                onCompleted(t)
            }
        }

        open fun onPrepare() {

        }

        abstract fun onBackground(): T
        abstract fun onCompleted(t: T)
    }

    class PriorityRunnable(val priority: Int, val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }

        override fun run() {
            runnable.run()
        }

    }

    /***
     * 阻塞线程
     */
    @Synchronized
    fun pause() {
        isPaused = true
        Log.e(TAG, "hiexecutor is paused")
    }

    /***
     * 唤醒所有阻塞的线程
     * 通过 Synchronized注解实现多线程下的操作
     */
    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            pasueCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.e(TAG, "hiexecutor is resumed")
    }
}

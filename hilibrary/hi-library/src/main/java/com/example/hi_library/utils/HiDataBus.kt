package com.example.hi_library.utils

import android.util.Log
import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/****
 * @author 栾桂明
 * @see LiveData
 * @date 2020年 1月19日
 * @desc 基于liveData实现的消息总线
 */
object HiDataBus {
    //使用hashMap存储事件和事件的名称
    private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    /***
     * 创建事件StickyLiveData
     */
    fun <T> with(eventName: String): StickyLiveData<T> {
        var liveData = eventMap[eventName]
        if (liveData == null) {
            liveData = StickyLiveData<T>(eventName)
            eventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    /***
     * 对liveData进行包装 对Version  observe方法进行改造
     */
    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {
        var mStickyData: T? = null

        //记录发送数据的个数
        var mVersion = 0

        /***
         * 主线程发送数据
         */
        fun setStickyData(stickyData: T) {
            mStickyData = stickyData

            setValue(stickyData)
        }

        /***
         * 不受线程的限制
         */
        fun postStickyData(stickyData: T) {
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner, false, observer)
        }

        fun observerSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    eventMap.remove(eventName)
                }
            })
            //观察者对当前的数据的具体处理
            super.observe(owner, StickyObserver(this, sticky, observer))
        }

    }

    class StickyObserver<T>(
        private val stickyLiveData: StickyLiveData<T>,
        private val sticky: Boolean,
        private val observer: Observer<in T>
    ) : Observer<T> {
        //为了控制粘性事件的分发
        private var lastVersion = stickyLiveData.mVersion

        override fun onChanged(t: T) {
            Log.e("lastVersion", "" + lastVersion)
            Log.e("mVersion", "" + stickyLiveData.mVersion)
            if (lastVersion >= stickyLiveData.mVersion) {
                //就说明stickyLiveData没有更新的数据
                if (sticky && stickyLiveData.mStickyData != null) {
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return

            }
            lastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }

    }

}


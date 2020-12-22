package com.example.hithrad

import android.content.Context
import android.os.AsyncTask
import android.util.Log

class ConcurrentTest {

    companion object {
        private val TAG = "ConcurrentTest"
        fun test(context: Context) {
            class MyAsyncTask : AsyncTask<String, Int, String>() {
                /***
                 * 处理耗时任务的回调方法
                 */
                override fun doInBackground(vararg params: String): String {
                    for (i in 1..10) {
                        //将耗时任务发送到UI线程
                        publishProgress(i * 10)
                    }
                    return params[0]
                }

                /***
                 * 耗时任务执行的进度的回调方法
                 */
                override fun onProgressUpdate(vararg values: Int?) {
                    Log.e(TAG, "onProgressUpdate" + values[0])
                }

                /***
                 * 获取耗时任务执行的结果
                 */
                override fun onPostExecute(result: String?) {
                    Log.e(TAG, "onProgressUpdate $result")
                }

            }
            //适用于需要知道任务执行的进度并更新ui的场景
            val task = MyAsyncTask()
            task.execute("execute task")
            //所有任务串行执行，即先来后到 但如果一条任务休眠了，后面的任务都将被阻塞
            AsyncTask.execute(object :Runnable{
                override fun run() {
                    Log.e(TAG,"run :AsyncTask execute")
                }

            })
            //线程池中执行
            AsyncTask.THREAD_POOL_EXECUTOR.execute(object :Runnable{
                override fun run() {
                    Log.e(TAG,"run :THREAD_POOL_EXECUTOR AsyncTask execute")
                }

            })
        }
    }
}
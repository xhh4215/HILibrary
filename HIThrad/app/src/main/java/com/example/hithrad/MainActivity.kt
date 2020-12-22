package com.example.hithrad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hithrad.ThreadInformation.testInformation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //AsyncTask 练习使用
//        ConcurrentTest.test(this)
        //HandlerThread练习使用
//        HandlerThreadTest.test()
        //线程生命周期 wait()
//        ThreadLifeCycleTest.testWait()
        //线程生命周期 join()
//        ThreadLifeCycleTest.testJoin()
        testInformation()
    }
}
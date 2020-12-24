package com.example.asproj.rote

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.asproj.R
import com.example.common.ui.view.EmptyView

/****
 * @author luanguiming
 * @date 2020年12月21日
 * @desc 全局的统一的错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag","加载错误页")

        setContentView(R.layout.layout_global_degrade)
         val emptyView = findViewById<EmptyView>(R.id.empty_view)
        emptyView.setIcon(R.string.if_unexpected)
        emptyView.setTitle(getString(R.string.degrade_tip))

    }
}
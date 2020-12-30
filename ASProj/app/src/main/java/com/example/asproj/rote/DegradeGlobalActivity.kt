package com.example.asproj.rote

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.R
import com.example.asproj.databinding.LayoutGlobalDegradeBinding

/****
 * @author luanguiming
 * @date 2020年12月21日
 * @desc 全局的统一的错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {
    private lateinit var degradeBinding: LayoutGlobalDegradeBinding

    //使用Arout自动对路由过来的数据的初始化
    @JvmField
    @Autowired
    var degrade_title: String? = null

    @JvmField
    @Autowired
    var degrade_desc: String? = null
    @JvmField
    @Autowired
    var degrade_action: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        Log.d("tag", "加载错误页")
        degradeBinding = DataBindingUtil.setContentView(this, R.layout.layout_global_degrade)
        degradeBinding.emptyView.setIcon(R.string.if_unexpected)
        if (degrade_title != null) {
            degradeBinding.emptyView.setTitle(degrade_title!!)
        }
        if (degrade_desc != null) {
            degradeBinding.emptyView.setDesc(degrade_desc!!)
        }
        if (degrade_action != null) {
            degradeBinding.emptyView.setHelperIcon(listener = View.OnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(degrade_action))
                startActivity(intent)
            })
        }
        degradeBinding.actionBack.setOnClickListener {
            onBackPressed()
        }


    }
}
package com.example.asproj.biz.notice

import android.accounts.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.asproj.R
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.accountapi.AccountApi
import com.example.asproj.http.model.CourseNotice
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.utils.HiStatusBarUtil
import com.example.hi_ui.ui.dataitem.HiAdapter
import kotlinx.android.synthetic.main.activity_notice_list.*

@Route(path = "/notice/list")
class NoticeListActivity : AppCompatActivity() {
    private lateinit var adapter: HiAdapter
    private lateinit var courseNotice: CourseNotice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiStatusBarUtil.setStatusBar(this, true, translucent = false)
        setContentView(R.layout.activity_notice_list)
        action_back.setOnClickListener {
            onBackPressed()
        }
        initUI()
        queryCourseNotice()
    }

    private fun queryCourseNotice() {
        ApiFactory.create(AccountApi::class.java).notice()
            .enqueue(object : HiCallBack<CourseNotice> {
                override fun onSuccess(response: HiResponse<CourseNotice>) {
                    if (response.successfull() && response.data != null) {
                        response.data?.let {
                            bindData(it)
                        }
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun bindData(data: CourseNotice) {
        courseNotice = data
        data.list?.map {
            adapter.addItemAt(
                0, NoticeItem(it)
                , true
            )

        }

    }

    private fun initUI() {
        val layoutManager = LinearLayoutManager(this)
        adapter = HiAdapter(this)
        list.layoutManager = layoutManager
        list.adapter = adapter

    }
}
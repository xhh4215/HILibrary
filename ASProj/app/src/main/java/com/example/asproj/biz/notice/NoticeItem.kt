package com.example.asproj.biz.notice

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.R
import com.example.asproj.http.model.Notice
import com.example.common.utils.DateUtil
import com.example.hi_library.utils.HiRes
import com.example.hi_ui.ui.dataitem.HiDataItem
import kotlinx.android.synthetic.main.layout_notice_item.view.*

class NoticeItem(val itemData: Notice) : HiDataItem<Notice, RecyclerView.ViewHolder>() {


    override fun getItemLayoutRes(): Int {
        return R.layout.layout_notice_item
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        itemData?.apply {
            holder.itemView.tv_title.text = title
            if ("goods" == type) {
                holder.itemView.icon.text = HiRes.getString(R.string.if_notice_recommend)
                holder.itemView.setOnClickListener {
                    ARouter.getInstance().build("/detail/main").withString("goodsId", url)
                        .navigation(holder.itemView.context)
                }
            } else {
                holder.itemView.icon.text = HiRes.getString(R.string.if_notice_msg)
                holder.itemView.setOnClickListener {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        ContextCompat.startActivity(holder.itemView.context, intent, null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            holder.itemView.tv_sub_title.text = subtitle
            holder.itemView.tv_publish_date.text = DateUtil.getMDDate(createTime)

        }
    }

}
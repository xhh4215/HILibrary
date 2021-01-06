package com.example.asproj.fragment.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.http.model.HomeBanner
import com.example.asproj.rote.HiRoute
import com.example.common.ui.view.loadUrl
import com.example.hi_library.log.utils.HiDisplayUtil
import com.example.hi_ui.ui.banner.core.HiBanner
import com.example.hi_ui.ui.banner.core.HiBannerMo
import com.example.hi_ui.ui.dataitem.HiDataItem

class BannerItem(val list: List<HomeBanner>) :
    HiDataItem<List<HomeBanner>, RecyclerView.ViewHolder>(list) {
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val banner = holder.itemView as HiBanner
        val models = mutableListOf<HiBannerMo>()
        list.forEachIndexed { index, homeBanner ->
            val bannerMo = object : HiBannerMo() {}
            bannerMo.url = homeBanner.cover
            models.add(bannerMo)
        }
        banner.setBannerData(models)
        banner.setBindAdapter { viewHolder, mo, position ->
            ((viewHolder.rootView) as ImageView).loadUrl(mo.url)
        }
        banner.setOnBannerClickListener { viewHolder, bannerMo, position ->
            HiRoute.startActivity4Browser(list[position].url)
        }
    }

    override fun getItemView(parent: ViewGroup): View? {
        val context = parent.context
        val banner = HiBanner(context)
        val params = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            HiDisplayUtil.dp2px(160f, context.resources)
        )
        params.bottomMargin = HiDisplayUtil.dp2px(10f)
        banner.layoutParams = params
        banner.setBackgroundColor(Color.WHITE)
        return banner
    }

}
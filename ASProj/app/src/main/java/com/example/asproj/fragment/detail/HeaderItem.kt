package com.example.asproj.fragment.detail

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.R
import com.example.asproj.http.model.DetailModel
import com.example.asproj.http.model.SliderImage
import com.example.common.ui.view.loadUrl
import com.example.hi_ui.ui.banner.core.HiBannerMo
import com.example.hi_ui.ui.banner.indicator.HiCircleIndicator
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_header.*

class HeaderItem(
    val sliderImage: List<SliderImage>?,
    val price: String?,
    val completedNumText: String?,
    val goodsName: String
) : HiDataItem<Any, HiViewHolder>() {
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val bannerItems = mutableListOf<HiBannerMo>()
        sliderImage?.forEach {
            val bannerMo = object : HiBannerMo() {}
            bannerMo.url = it.url
            bannerItems.add(bannerMo)
        }
        holder.hi_banner.setHiIndicator(HiCircleIndicator(context))
        holder.hi_banner.setBannerData(bannerItems)
        holder.hi_banner.setBindAdapter { viewHolder, mo, position ->
            val imageView = viewHolder.rootView as ImageView
            mo.let {
                imageView.loadUrl(mo.url)
            }
        }
        holder.price.text = spanPrice(price)
        holder.sale_desc.text = completedNumText
        holder.title.text = goodsName

    }

    /***
     * 为价格添加复文本的效果
     */
    private fun spanPrice(price: String?): CharSequence {
        if (TextUtils.isEmpty(price)) return ""
        val ss = SpannableString(price)
        ss.setSpan(AbsoluteSizeSpan(18, true), 1, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss
    }

    override fun getItemLayoutRes() = R.layout.layout_detail_item_header


}
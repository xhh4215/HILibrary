package com.example.asproj.fragment.detail

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asproj.R
import com.example.asproj.fragment.home.GoodsItem
import com.example.asproj.http.model.DetailModel
import com.example.asproj.http.model.GoodsModel
import com.example.asproj.http.model.Shop
import com.example.common.ui.view.loadUrl
import com.example.hi_ui.ui.dataitem.HiAdapter
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_shop.*
import kotlinx.android.synthetic.main.layout_detail_item_shop_goods_item.*

class ShopItem(val detailModel: DetailModel) : HiDataItem<DetailModel, HiViewHolder>() {
    private val SHOP_GOODS_ITEM_SPAN_COUNT = 3
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val shop: Shop? = detailModel.shop
        shop?.let {
            holder.shop_logo.loadUrl(it.logo)
            holder.shop_title.text = it.name
            holder.shop_desc.text =
                String.format(context.getString(R.string.shop_desc), it.goodsNum, it.completedNum)
            val evaluation: String? = shop.evaluation
            evaluation?.let {
                val tagContainer = holder.tag_container
                tagContainer.visibility = View.VISIBLE
                val serviceTags = evaluation.split(" ")
                var index = 0
                // 6个元素每两个组成一个标签
                for (tagIndex in 0 until serviceTags.size / 2) {
                    val tagView = if (index < tagContainer.childCount) {
                        tagContainer.getChildAt(index) as TextView
                    } else {
                        val tagView = TextView(context)
                        val params =
                            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                        params.weight = 1f
                        tagView.layoutParams = params
                        tagView.gravity = Gravity.CENTER
                        tagView.textSize = 14f
                        tagView.setTextColor(ContextCompat.getColor(context, R.color.color_666))

                        tagContainer.addView(tagView)
                        tagView
                    }
                    val serviceName =if (index>=serviceTags.size)continue else serviceTags[index]
                    val serviceTag = serviceTags[index + 1]
                    index += 2
                    val spanTag = spanServiceTag(context, serviceName, serviceTag)
                    tagView.text = spanTag
                }
            }

        }

        val flowGoods = detailModel.flowGoods
        flowGoods?.let {
            val flowRecyclerView = holder.flow_recycler_view
            flowRecyclerView.visibility = View.VISIBLE
            if (flowRecyclerView.layoutManager == null) {
                flowRecyclerView.layoutManager =
                    GridLayoutManager(context, SHOP_GOODS_ITEM_SPAN_COUNT)
            }
            if (flowRecyclerView.adapter == null) {
                flowRecyclerView.adapter = HiAdapter(context)
            }
            val dataItems = mutableListOf<GoodsItem>()
            it.forEach { item ->
                dataItems.add(ShopGoodItem(item))
            }
            val adapter = flowRecyclerView.adapter as HiAdapter
            adapter.clearItems()
            adapter.addItems(dataItems, true)

        }
    }

    private inner class ShopGoodItem(goodsModel: GoodsModel) : GoodsItem(goodsModel, false) {
        override fun getItemLayoutRes(): Int {
            return R.layout.layout_detail_item_shop_goods_item
        }

        override fun onViewAttachedToWindow(holder: HiViewHolder) {
            super.onViewAttachedToWindow(holder)
            val viewParent = holder.itemView.parent as ViewGroup
            val availableWidth =
                viewParent.measuredWidth - viewParent.paddingLeft - viewParent.paddingRight
            val itemWidth = availableWidth / SHOP_GOODS_ITEM_SPAN_COUNT
            val itemImage = holder.item_image
            val params = itemImage.layoutParams
            params.width = itemWidth
            params.height = itemWidth
        }
    }

    /***
     * 复合文本的处理
     */
    private fun spanServiceTag(
        context: Context,
        serviceName: String,
        serviceTag: String
    ): CharSequence {
        val ss = SpannableString(serviceTag)
        val ssb = SpannableStringBuilder()
        ss.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_c61)),
            0,
            ss.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(context, R.color.color_f8e)),
            0,
            ss.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(serviceName)
        return ssb
    }

    override fun getItemLayoutRes() = R.layout.layout_detail_item_shop
}
package com.example.asproj.fragment.home

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.R
import com.example.asproj.http.model.GoodsModel
import com.example.common.ui.view.loadUrl
import com.example.hi_library.utils.HiDisplayUtil
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.*
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.view.*
import kotlinx.android.synthetic.main.layout_home_operation_grid_item.view.item_image
import kotlinx.android.synthetic.main.layout_home_operation_grid_item.view.item_title

class GoodsItem(val goodsModel: GoodsModel, val hotTab: Boolean) :
    HiDataItem<GoodsModel, HiViewHolder>(goodsModel) {
    val MAX_TAG_SIZE = 3
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.item_image.loadUrl(goodsModel.sliderImage)
        holder.item_title.text = goodsModel.goodsName
        holder.item_price.text = goodsModel.marketPrice
        holder.item_sale_desc.text = goodsModel.completedNumText
        val itemLabelContainer = holder.item_label_container
        if (!TextUtils.isEmpty(goodsModel.tags)) {
            itemLabelContainer.visibility = View.VISIBLE
            val split = goodsModel.tags.split(" ")

            for (index in split.indices) {
                val childCount = itemLabelContainer.childCount
                if (index > MAX_TAG_SIZE - 1) {
                    for (index in childCount - 1 downTo MAX_TAG_SIZE - 1) {
                        itemLabelContainer.removeViewAt(index)
                    }
                    break
                }
                val labelView: TextView = if (index > childCount - 1) {
                    val view = createLabelView(context, index != 0)
                    itemLabelContainer.addView(view)
                    view
                } else {
                    itemLabelContainer.getChildAt(index) as TextView
                }

                labelView.text = split[index]
            }
        } else {
            itemLabelContainer.visibility = View.GONE
        }
        if (!hotTab) {
            val margin = HiDisplayUtil.dp2px(2f)
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            val parentLeft = hiAdapter?.getAttachRecyclerView()?.left ?: 0
            val parentPaddingLeft = hiAdapter?.getAttachRecyclerView()?.paddingLeft ?: 0
            val itemLeft = holder.itemView.left
            if (itemLeft == (parentLeft + parentPaddingLeft)) {
                params.rightMargin = margin
            } else {
                params.leftMargin = margin
            }
            holder.itemView.layoutParams = params
        }
    }

    override fun getItemLayoutRes(): Int {
        return if (hotTab) R.layout.layout_home_goods_list_item1 else R.layout.layout_home_goods_list_item2
    }

    private fun createLabelView(context: Context, withLeftMargin: Boolean): TextView {
        val labelView = TextView(context)
        labelView.setTextColor(ContextCompat.getColor(context, R.color.color_e75))
        labelView.setBackgroundResource(R.drawable.shape_goods_label)
        labelView.textSize = 11f
        labelView.gravity = Gravity.CENTER
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, HiDisplayUtil.dp2px(16f)
        )
        param.leftMargin = if (withLeftMargin) HiDisplayUtil.dp2px(5f) else 0
        labelView.layoutParams = param
        return labelView
    }

    override fun getSpanSize(): Int {
        return if (hotTab) super.getSpanSize() else 1
    }

}
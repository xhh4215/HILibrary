package com.example.asproj.fragment.home

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.BR
import com.example.asproj.R
import com.example.asproj.http.model.GoodsModel
import com.example.asproj.rote.HiRoute
import com.example.hi_library.utils.HiDisplayUtil
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.*

open class GoodsItem(private val goodsModel: GoodsModel, private val hotTab: Boolean) :
    HiDataItem<GoodsModel, GoodsItem.GoodItemHolder>(goodsModel) {
    private val MAX_TAG_SIZE = 3
    override fun onBindData(holder: GoodsItem.GoodItemHolder, position: Int) {
        val context = holder.itemView.context
        holder.binding.setVariable(BR.goodModel, goodsModel)
        val itemLabelContainer = holder.item_label_container
        if (itemLabelContainer != null) {
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
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("goodId", goodsModel.goodsId)
            bundle.putParcelable("goodModel", goodsModel)
            HiRoute.startActivity(context, bundle, HiRoute.Destination.DETAIL_MAIN)
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

    override fun onCreateViewHolder(parent: ViewGroup): GoodItemHolder? {
        val inflate = from(parent.context)
        var binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflate, getItemLayoutRes(), parent, false)
        return GoodItemHolder(binding)
    }

    override fun getSpanSize(): Int {
        return if (hotTab) super.getSpanSize() else 1
    }

    class GoodItemHolder(var binding: ViewDataBinding) : HiViewHolder(binding.root) {


    }
}
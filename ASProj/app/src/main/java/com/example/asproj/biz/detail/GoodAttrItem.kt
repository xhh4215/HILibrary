package com.example.asproj.biz.detail


import android.view.LayoutInflater
import android.view.View
import com.example.asproj.R
import com.example.asproj.http.model.DetailModel
import com.example.common.ui.view.InputItemLayout
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_attr.*

/**
 * 详情页--商品详情描述模块
 */
class GoodAttrItem(val detailModel: DetailModel) : HiDataItem<DetailModel, HiViewHolder>() {
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val goodAttr = detailModel.goodAttr
        try {
            goodAttr?.let {
                val iterator = it.iterator()
                var index = 0
                val attrContainer = holder.attr_container
                attrContainer.visibility = View.VISIBLE
                while (iterator.hasNext()) {
                    val attr = iterator.next()
                    val entries = attr.entries
                    val key = entries.first().key
                    val value = entries.first().value

                    val attrItemView: InputItemLayout = if (index < attrContainer.childCount) {
                        attrContainer.getChildAt(index)
                    } else {
                        LayoutInflater.from(context)
                            .inflate(
                                R.layout.layout_detail_item_attr_item,
                                attrContainer,
                                false
                            )
                    } as InputItemLayout

                    attrItemView.editText.hint = value
                    attrItemView.editText.isEnabled = false
                    attrItemView.titleTextView.text = key

                    if (attrItemView.parent == null) {
                        attrContainer.addView(attrItemView)
                    }
                    index++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        detailModel.goodDescription?.let {
            val attrDesc = holder.attr_desc
            attrDesc.visibility = View.VISIBLE
            attrDesc.text = it
        }
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_attr
    }
}
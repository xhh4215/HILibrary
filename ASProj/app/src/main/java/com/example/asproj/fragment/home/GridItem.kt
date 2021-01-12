package com.example.asproj.fragment.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.R
import com.example.asproj.http.model.Subcategory
import com.example.asproj.rote.HiRoute
import com.example.common.ui.view.loadUrl
import com.example.hi_library.utils.HiDisplayUtil
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.layout_home_operation_grid_item.*

class GridItem(val list: List<Subcategory>) :
    HiDataItem<List<Subcategory>, HiViewHolder>(list) {
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context
        val gridView = holder.itemView as RecyclerView
        gridView.adapter = GridAdapter(context, list)

    }


    override fun getItemView(parent: ViewGroup): View? {
        val context = parent.context
        val gridView = RecyclerView(context)
        val params = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = HiDisplayUtil.dp2px(10f)
        gridView.layoutManager = GridLayoutManager(parent.context, 5)
        gridView.layoutParams = params
        gridView.setBackgroundColor(Color.WHITE)
        return gridView
    }


    inner class GridAdapter(val context: Context, val list: List<Subcategory>) :
        RecyclerView.Adapter<HiViewHolder>() {
        private val inflate: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = inflate.inflate(R.layout.layout_home_operation_grid_item, parent, false)
            return HiViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            val subcategory = list[position]
            holder.item_image.loadUrl(subcategory.subcategoryIcon)
            holder.item_title.text = subcategory.subcategoryName
            holder.itemView.setOnClickListener {
                // 是应该跳转到类目的商品列表页
                val bundle = Bundle()
                bundle.putString("categoryId",subcategory.categoryId)
                bundle.putString("categoryTitle",subcategory.subcategoryName)
                bundle.putString("subcategoryId",subcategory.subcategoryId)
                HiRoute.startActivity(context,bundle, HiRoute.Destination.GOODS_LIST)

            }
        }

    }

}
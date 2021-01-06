package com.example.asproj.fragment.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.R
import com.example.asproj.http.model.Subcategory
 import com.example.common.ui.view.loadUrl
import com.example.hi_library.log.utils.HiDisplayUtil
import com.example.hi_ui.ui.dataitem.HiDataItem
import kotlinx.android.synthetic.main.layout_home_operation_grid_item.view.*

class GridItem(val list: List<Subcategory>) :
    HiDataItem<List<Subcategory>, RecyclerView.ViewHolder>(list) {
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
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
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val inflate: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = inflate.inflate(R.layout.layout_home_operation_grid_item, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val subcategory = list[position]
            holder.itemView.item_image.loadUrl(subcategory.subcategoryIcon)
            holder.itemView.item_title.text = subcategory.subcategoryName
            holder.itemView.setOnClickListener {

            }
        }

    }

}
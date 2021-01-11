package com.example.asproj.fragment.category

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoryItemDecoration(val callback: (Int) -> String) : RecyclerView.ItemDecoration() {

    /***
     * @param  view  当前的recyclerview中的item对应的View
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //1 根据View对象，找到他在列表中处于的位置  adapterPosition
        val adapterPosition = parent.getChildAdapterPosition(view)
        // 判断adapterPosition的合法性
        if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) return
        //2 拿到当前位置的 adapterPosition 对应的groupname
        val groupName= callback(adapterPosition)

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}
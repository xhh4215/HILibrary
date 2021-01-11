package com.example.asproj.fragment.category

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hi_library.utils.HiDisplayUtil
import kotlin.system.exitProcess

class CategoryItemDecoration(val callback: (Int) -> String,val spanCount:Int) : RecyclerView.ItemDecoration() {
    private val groupFirstPositions = mutableMapOf<String, Int>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

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
        val groupName = callback(adapterPosition)
        //3 拿到前一个位置的groupname
        val preGroupName = if (adapterPosition > 0) callback(adapterPosition - 1) else null
        //4 判断是不是同一组
        val sameGroup = TextUtils.equals(groupName, preGroupName)
        if (!sameGroup && !groupFirstPositions.containsKey(groupName)) {
            //就说明当前位置adapterPosition   对应的item 是 当前组的 第一个位置。
            //此时 咱们存储起来，记录下来，目的是为了方便后面扥计算，计算 后面item  是否是第一行
            groupFirstPositions[groupName] = adapterPosition
        }
        val firstRowPosition = groupFirstPositions[groupName]?:0
        val sameRow = adapterPosition-firstRowPosition in 0..spanCount
        if (!sameGroup || sameRow) {
            outRect.set(0, HiDisplayUtil.dp2px(40f), 0, 0)
            return
        }

        outRect.set(0, 0, 0, 0)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (index in 0 until childCount) {
            val view = parent.getChildAt(index)
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) continue
            val groupName = callback(adapterPosition)

            //判断当前位置 是不是分组的第一个位置
            //如果是，咱们在他的位置上绘制标题
            val groupFirstPosition = groupFirstPositions[groupName]
            if (groupFirstPosition == adapterPosition) {
                val decorationBounds = Rect()
                //为了拿到当前item 的 左上右下的坐标信息 包含了，margin 和 扩展空间的
                parent.getDecoratedBoundsWithMargins(view, decorationBounds)

                val textBounds = Rect()
                paint.getTextBounds(groupName, 0, groupName.length, textBounds)
                paint.textSize = HiDisplayUtil.dp2px(16f).toFloat()
                c.drawText(
                    groupName,
                    HiDisplayUtil.dp2px(16f).toFloat(),
                    (decorationBounds.top + 2 * textBounds.height()).toFloat(),
                    paint
                )
            }
        }
    }
    fun clear() {
        groupFirstPositions.clear()
    }
}
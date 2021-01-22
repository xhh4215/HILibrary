package com.example.asproj.fragment.detail

import android.content.res.ColorStateList
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.asproj.R
import com.example.asproj.http.model.DetailModel
import com.example.common.ui.view.loadCircle
import com.example.hi_library.utils.HiDisplayUtil
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.dataitem.HiViewHolder
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.layout_detail_item_comment.*
import kotlinx.android.synthetic.main.layout_detail_item_comment_item.view.*
import kotlin.math.min

class CommentItem(val model: DetailModel) : HiDataItem<DetailModel, HiViewHolder>() {
    override fun onBindData(holder: HiViewHolder, position: Int) {
        holder.comment_title.text = model.commentCountTitle
        val context = holder.itemView.context ?: return
        val commentTag = model.commentTags
        val tagsArrays = commentTag?.split(" ")
        if (tagsArrays != null && tagsArrays.isNotEmpty()) {
            for (index in tagsArrays.indices) {
                //会存在滑动复用的问题  所以创建标签的时候应该检查复用
                val chipGroup = holder.chip_group
                val chipLabel = if (index < chipGroup.childCount) {
                    chipGroup.getChildAt(index) as Chip
                } else {
                    val chipLabel = Chip(context)
                    chipLabel.chipCornerRadius = HiDisplayUtil.dp2px(8f).toFloat()
                    chipLabel.chipBackgroundColor =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_faf0))
                    chipLabel.setTextColor(ContextCompat.getColor(context, R.color.color_999))
                    chipLabel.textSize = 14f
                    chipLabel.gravity = Gravity.CENTER
                    // 不可以显示任何的icon
                    chipLabel.isCheckedIconVisible = false
                    chipLabel.isCheckable = false
                    chipLabel.isChipIconVisible = false
                    holder.chip_group.addView(chipLabel)
                    chipLabel
                }
                chipLabel.text = tagsArrays[index]
            }
        }
        model.commentModels?.let {
            Log.d("size",  ""+model.commentModels.size)
            val commentContainer = holder.comment_container
            for (index in 0..min(it.size - 1, 3)) {
                val comment = it[index]
                val itemView = if (index < commentContainer.childCount) {
                    commentContainer.getChildAt(index)
                } else {
                    val view = LayoutInflater.from(context)
                        .inflate(R.layout.layout_detail_item_comment_item, commentContainer, false)
                    commentContainer.addView(view)
                    view
                }
                itemView.user_avatar.loadCircle(comment.avatar)
                itemView.user_name.text = comment.nickName
                itemView.comment_content.text = comment.content

            }
        }
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_comment
    }

}
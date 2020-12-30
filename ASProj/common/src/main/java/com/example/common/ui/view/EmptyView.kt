package com.example.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.example.common.R
import org.w3c.dom.Text
import java.util.jar.Attributes

class EmptyView : LinearLayout {
    private var desc: TextView
    private var icon: TextView
    private var title: TextView
    private var refresh: Button
    private var actionHelper: IconFontTextView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
        icon = findViewById(R.id.empty_icon)
        refresh = findViewById(R.id.empty_action)
        desc = findViewById(R.id.empty_text)
        title = findViewById(R.id.empty_title)
        actionHelper = findViewById(R.id.empty_tips)

    }


    fun setIcon(@StringRes iconRes: Int) {
        icon.setText(iconRes)
    }


    fun setDesc(text: String) {
        desc.text = text
        desc.visibility = if (TextUtils.isDigitsOnly(text)) View.GONE else View.VISIBLE

    }

    fun setTitle(text: String) {
        title.text = text
        title.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE

    }

    @JvmOverloads
    fun setHelperIcon(action: Int = R.string.if_detail, listener: View.OnClickListener) {
        actionHelper.setText(action)
        actionHelper.setOnClickListener { listener }
        actionHelper.visibility = View.VISIBLE
        if (action==-1){
            actionHelper.visibility = View.GONE
        }
    }

    fun setRefresh(text: String, listener: OnClickListener) {
        if (TextUtils.isEmpty(text)) {
            refresh.visibility = View.GONE
        } else {
            refresh.visibility = View.VISIBLE
            refresh.setOnClickListener(listener)
        }

    }

}
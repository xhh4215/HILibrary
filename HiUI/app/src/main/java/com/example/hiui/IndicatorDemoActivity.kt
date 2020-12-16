package com.example.hiui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.hi_ui.ui.banner.core.HiBanner
import com.example.hi_ui.ui.banner.core.HiBannerMo
import com.example.hi_ui.ui.banner.indicator.HiCircleIndicator
import com.example.hi_ui.ui.banner.indicator.HiIndicator

class IndicatorDemoActivity : AppCompatActivity() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )
    private var autoPlay: Boolean = false
    private var hiIndicator: HiIndicator<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indicator_demo)
        initView(HiCircleIndicator(this), false)
        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initView(hiIndicator, autoPlay)
        }
        findViewById<View>(R.id.tv_switch).setOnClickListener {
            if (hiIndicator is HiCircleIndicator) {
//                initView()
            } else {
                initView(HiCircleIndicator(this), autoPlay)
            }
        }


    }

    private fun initView(hiIndicator: HiIndicator<*>?, autoPaly: Boolean) {
        val banner = findViewById<HiBanner>(R.id.banner)
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..7) {
            val mo = BannerMo();
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        banner.setHaIndicator(hiIndicator)
        banner.setAutoPlay(autoPaly)
        banner.setIntervalTime(2000)
        //设置自定义布局
        banner.setBannerData(R.layout.banner_item_layout, moList)
        banner.setBindAdapter { viewHolder, mo, position ->
            val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
            Log.d("lgm",mo.url)
            Glide.with(this@IndicatorDemoActivity).load(mo.url).into(imageView)
            val textView: TextView = viewHolder.findViewById(R.id.tv_title)
            textView.text = mo.url

        }

    }
}
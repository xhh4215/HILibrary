package com.example.hi_ui.ui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.viewpager.widget.ViewPager;

import com.example.hi_ui.ui.banner.indicator.HiIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/***
 * @description HIBanner组件对外提供功能的接口
 * @author 栾桂明
 * @date 2020 年 1月6日
 */
public interface IHiBanner {
    /***
     * 设置HIBanner组件绑定的数据
     * @param layoutResId
     * @param models
     */
    void setBannerData(@LayoutRes int layoutResId, @NotNull List<? extends HiBannerMo> models);

    void setBannerData(@NotNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator<?> haIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setScrollDuration(int duration);


    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(HiBanner.OnBannerClickListener onBannerClickListener);

    interface OnBannerClickListener {
        void onBannerClick(@NotNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NotNull HiBannerMo bannerMo, int position);
    }
}

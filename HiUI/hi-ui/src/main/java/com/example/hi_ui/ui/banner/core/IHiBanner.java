package com.example.hi_ui.ui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.viewpager.widget.ViewPager;

import com.example.hi_ui.ui.banner.indicator.HiIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IHiBanner {
    void setBannerData(@LayoutRes int layoutResId, @NotNull List<? extends HiBannerMo> models);

    void setBannerData(@NotNull List<? extends HiBannerMo> models);

    void setHaIndicator(HiIndicator<?> haIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setScrollDuration(int duration);


    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    interface OnBannerClickListener {
        void onBannerClick(@NotNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NotNull HiBannerMo bannerMo, int position);
    }
}

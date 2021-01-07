package com.example.hi_ui.ui.banner.core;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.hi_ui.R;
import com.example.hi_ui.ui.banner.indicator.HiCircleIndicator;
import com.example.hi_ui.ui.banner.indicator.HiIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/***
 * HiBanner的控制器
 * 辅助HiBanner完成各种功能的控制
 * 将HiBanner的一些逻辑内聚在这，保证暴露给使用者的HiBanner干净整洁
 */
public class HiBannerDelegate implements IHiBanner, ViewPager.OnPageChangeListener {
    private Context mContext;
    private HiBanner mBanner;
    private HiBannerAdapter mAdapter;
    private HiIndicator<?> mHiIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends HiBannerMo> mHiBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private IHiBanner.OnBannerClickListener mOnBannerClickListener;
    private HiViewPager mHiViewPager;
    private int mScrollDuration = -1;

    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        this.mContext = context;
        this.mBanner = hiBanner;
    }

    @Override
    public void setBannerData(int layoutResId, @NotNull List<? extends HiBannerMo> models) {
        mHiBannerMos = models;
        init(layoutResId);
    }


    @Override
    public void setBannerData(@NotNull List<? extends HiBannerMo> models) {
        setBannerData(R.layout.hi_banner_item_image, models);
    }
    public void setAdapter(HiBannerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(HiBanner.OnBannerClickListener onBannerClickListener) {
          this.mOnBannerClickListener = onBannerClickListener;

    }

    @Override
    public void setHiIndicator(HiIndicator<?> haIndicator) {
        this.mHiIndicator = haIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mAdapter != null) {
            mAdapter.setAutoPlay(autoPlay);
        }
        if (mHiViewPager != null) {
            mHiViewPager.setAutoPlay(autoPlay);
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    /***
     * 设置ViewPager内部的切换速度
     * @param duration
     */
    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (mHiViewPager != null && duration > 0) {
            mHiViewPager.setScrollDuration(duration);
        }
    }


    private void init(int layoutResId) {

        Log.d("tag","1");
        if (mAdapter == null) {
            mAdapter = new HiBannerAdapter(mContext);

        }
        if (mHiIndicator == null) {
            mHiIndicator = new HiCircleIndicator(mContext);
        }
        mHiIndicator.onInflate(mHiBannerMos.size());
        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mHiBannerMos);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setOnBannerClickListener(mOnBannerClickListener);
        mHiViewPager = new HiViewPager(mContext);
        mHiViewPager.setIntervalTime(mIntervalTime);
        mHiViewPager.setAutoPlay(mAutoPlay);
        mHiViewPager.addOnPageChangeListener(this);
        mHiViewPager.setAdapter(mAdapter);
        if (mScrollDuration > 0) {
            mHiViewPager.setScrollDuration(mScrollDuration);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (mLoop || mAutoPlay && mAdapter.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，以达到无限滚动的效果
            int firstItem = mAdapter.getFirstItem();
            mHiViewPager.setCurrentItem(firstItem, false);
        }
        // 清除所有的View
        mBanner.removeAllViews();
        mBanner.addView(mHiViewPager, params);
        mBanner.addView(mHiIndicator.get(), params);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position / mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mHiIndicator != null) {
            mHiIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}

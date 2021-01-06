package com.example.hi_ui.ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HiBannerAdapter extends PagerAdapter {
    private Context mContext;
    private SparseArray<HiBannerViewHolder> mCacheViews = new SparseArray<>();
    private IHiBanner.OnBannerClickListener mBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends HiBannerMo> models;

    public HiBannerAdapter(Context context) {
        this.mContext = context;
    }

    public void setBannerData(@NotNull List<? extends HiBannerMo> models) {
        this.models = models;
        //初始化数据
        initCachedView();
        notifyDataSetChanged();
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }


    public void setOnBannerClickListener(IHiBanner.OnBannerClickListener onBannerClickListener) {
        this.mBannerClickListener = onBannerClickListener;
    }

    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }


    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    /***
     * 是否开启自动轮播
     */
    private boolean mAutoPlay = true;
    /***
     * 非自动轮播状态下是否可以循环切换
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;


    @Override
    public int getCount() {
        //无限轮播
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /***
     * 获取Banner页面的数量
     * @return
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();

    }

    /***
     * 获取初次展示的item的位置
     * @return
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /***
     * 实例化item的方法
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        HiBannerViewHolder viewHolder = mCacheViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        onBind(viewHolder, models.get(realPosition), position);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都能刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    protected void onBind(@NotNull final HiBannerViewHolder viewHolder, @NotNull final HiBannerMo bannerMo, int position) {
        viewHolder.rootView.setOnClickListener(v -> {
            if (mBannerClickListener != null) {
                mBannerClickListener.onBannerClick(viewHolder, bannerMo, position);
            }
        });
        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, bannerMo, position);
        }
    }

    private void initCachedView() {
        mCacheViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCacheViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }

    public static class HiBannerViewHolder {

        private SparseArray<View> viewSparseArray;
        public View rootView;

        public HiBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public SparseArray<View> getViewSparseArray() {
            return viewSparseArray;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>(1);
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.viewSparseArray.put(id, childView);

            }
            return childView;
        }

    }
}

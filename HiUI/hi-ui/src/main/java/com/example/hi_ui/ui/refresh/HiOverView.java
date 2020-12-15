package com.example.hi_ui.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hi_library.log.utils.HiDisplayUtil;

/***
 * 下拉刷新的Overlay视图，可以重载这个类来定义自己的Overlay
 */
public abstract class HiOverView extends FrameLayout {
    public enum HiRefreshState {
        /***
         * 初始态
         */
        STATE_INIT,
        /***
         * Header展示的状态
         */
        STATE_VISIBLE,
        /***
         * 刷新中的状态
         */
        STATE_REFRESH,
        /***
         * 超出可刷新距离的状态
         */
        STATE_OVER,
        /***
         * 超出刷新位置松手后的状态
         */
        STATE_OVER_RELEASE

    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;
    /***
     * 触发下啦刷新的最小高度
     */
    public int mPullRefreshHeight;
    /***
     * 最小阻尼
     */
    public float minDamp = 1.6f;
    /***
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public HiOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = HiDisplayUtil.dp2px(66, getResources());
        init();
    }

    /***
     * 初始化
     */
    public abstract void init();

    /***
     *  来告诉头部的实现类 下拉刷新的高度
     */
    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /***
     * 显示视图层的回调方法
     */
    protected abstract void onVisible();

    /***
     * 超过视图高度 释放就会加载
     */
    public abstract void onOver();

    /***
     * 开始刷新
     */
    public abstract void onRefresh();

    /***
     * 刷新完成
     */
    public abstract void onFinish();

    /***
     * 设置下拉刷新的状态
     * @param mState
     */
    public void setState(HiRefreshState mState) {
        this.mState = mState;
    }

    public HiRefreshState getState() {
        return mState;
    }
}

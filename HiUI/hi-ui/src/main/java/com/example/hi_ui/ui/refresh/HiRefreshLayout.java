package com.example.hi_ui.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import  com.example.hi_ui.ui.refresh.HiOverView.HiRefreshState;


/***
 * @date 2020年12月15日
 * @desc 下拉刷新组件的容器控件
 * @autohr 栾桂明
 */
public class HiRefreshLayout extends FrameLayout implements HiRefresh {
    //标识刷新的状态
    private HiRefreshState mState;
    //手势管理对象
    private GestureDetector mGestureDetector;
    //下拉刷新的监听
    private HiRefresh.HiRefreshListener mHiRefreshListener;
    //下拉刷新的头
    protected HiOverView mHiOverView;
    //下拉的最后的Y轴的高度
    private int mLastY;
    //刷新的时候是否禁止滚动
    private boolean disableRefreshScroll;
    //下拉刷新的自动滚动
    private AutoScroller mScroller;

    public HiRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }
    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /****
     * 初始化的操作
     */
    private void init() {
        mGestureDetector = new GestureDetector(getContext(), hiGestureDetector);
        mScroller = new AutoScroller();
    }

    /***
     * 设置下拉刷新的时候是否禁止滚动
     * @param disableRefreshScroll 是否禁止滚动
     */
    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    /***
     * 刷新完成的回调
     */
    @Override
    public void refreshFinished() {
        View head = getChildAt(0);
        mHiOverView.onFinish();
        mHiOverView.setState(HiOverView.HiRefreshState.STATE_INIT);
        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = HiOverView.HiRefreshState.STATE_INIT;
    }

    /***
     * 设置下拉刷新的回调
     * @param hiRefreshListener
     */
    @Override
    public void setRefreshListener(HiRefreshListener hiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener;
    }

    /***
     * 设置下拉刷新的头部
     * @param hiOverView
     */
    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        if (this.mHiOverView != null) {
            removeView(mHiOverView);
        }
        this.mHiOverView = hiOverView;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mHiOverView, 0, params);
    }

    /***
     * head和child 的重新布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //获取头部
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            if (mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                head.layout(0, mHiOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, mHiOverView.mPullRefreshHeight);
                child.layout(0, mHiOverView.mPullRefreshHeight, right, mHiOverView.mPullRefreshHeight + child.getMeasuredHeight());

            } else {
                head.layout(0, childTop - head.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }
            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
        }
    }

    /****
     * 关于手势的处理
     */
    HiGestureDetector hiGestureDetector = new HiGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY) || mHiRefreshListener != null && !mHiRefreshListener.enableRefresh()) {
                //横向滑动 或者刷新被禁止的时候不做处理
                return false;
            }
            //刷新的时候是否禁止滚动
            if (disableRefreshScroll && mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                return true;
            }
            View head = getChildAt(0);
            View child = HiScrollerUtil.findScrollableChild(HiRefreshLayout.this);
            if (HiScrollerUtil.childScrolled(child)) {
                //如果列表发生滚动则不处理
                return false;
            }
            //没有刷新或没有达到可刷新的距离  ，且头部已经划出或下拉
            if ((mState != HiOverView.HiRefreshState.STATE_REFRESH || head.getBottom() <= mHiOverView.mPullRefreshHeight) && (head.getBottom() > 0 || distanceY <= 0.0f)) {
                //还在滑动中
                if (mState != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    // 速度计算
                    if (child.getTop() < mHiOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / mHiOverView.minDamp);
                    } else {
                        seed = (int) (mLastY / mHiOverView.maxDamp);
                    }
                    //如果是正在刷新状态，则不可以在滑动的过程中改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) -distanceY;
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /***
     * 根据偏移量移动header和child
     * @param offsetY  偏移量
     * @param noAuto  是否非自动滚动触发
     * @return
     */
    private boolean moveDown(int offsetY, boolean noAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) {
            offsetY = -child.getTop();
            //移动head和child的位置到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != HiRefreshState.STATE_REFRESH) {
                mState =  HiRefreshState.STATE_INIT;
            }
        } else if (mState == HiRefreshState.STATE_REFRESH && childTop > mHiOverView.mPullRefreshHeight) {
            //如果正在下拉刷新中 ，禁止继续下拉
            return false;

        } else if (childTop <= mHiOverView.mPullRefreshHeight) {
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_VISIBLE && noAuto) {
                //头部开始显示
                mHiOverView.onVisible();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_VISIBLE);
                mState = HiOverView.HiRefreshState.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mHiOverView.mPullRefreshHeight && mState == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                refresh();
            }
        } else {
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_OVER && noAuto) {
                // 超出刷新位置
                mHiOverView.onOver();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mHiOverView != null) {
            mHiOverView.onScroll(head.getBottom(), mHiOverView.mPullRefreshHeight);
        }
        return true;
    }

    /***
     * 刷新
     */
    private void refresh() {
        if (mHiRefreshListener != null) {
            mState = HiOverView.HiRefreshState.STATE_REFRESH;
            mHiOverView.onRefresh();
            mHiOverView.setState(HiOverView.HiRefreshState.STATE_REFRESH);
            mHiRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (!mScroller.isIsFinished()) {
            return false;
        }
        View head = getChildAt(0);
        Log.d("height", head.getBottom() + "");
        if (ev.getAction() == MotionEvent.ACTION_UP ||
                ev.getAction() == MotionEvent.ACTION_CANCEL ||
                ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //用户松开手
            if (head.getBottom() > 0) {
                if (mState != HiOverView.HiRefreshState.STATE_REFRESH) { // 非正在刷新的状态
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if (consumed || mState != HiOverView.HiRefreshState.STATE_INIT && mState != HiOverView.HiRefreshState.STATE_REFRESH && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
            return super.dispatchTouchEvent(ev);
        }
        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private void recover(int dis) {
        if (mHiRefreshListener != null && dis > mHiOverView.mPullRefreshHeight) {
            //滚动到指定位置
            mScroller.recover(dis - mHiOverView.mPullRefreshHeight);
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            mScroller.recover(dis);
        }
    }

    /***
     * 借助Scroller实现视图的自动的滚动
     */
    private class AutoScroller implements Runnable {
        private Scroller scroller;
        private int mLastY;
        private boolean mIsFinished;

        public AutoScroller() {
            scroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (scroller.computeScrollOffset()) { //还未滚动完成
                moveDown(mLastY - scroller.getCurrY(), false);
                mLastY = scroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int dis) {
            if (dis <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            scroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isIsFinished() {
            return mIsFinished;
        }
    }
}

package com.example.hi_ui.ui.refresh;

/****
 * @date 2020年12月15日
 * @desc 下拉刷新组建提供的通用功能
 * @author 栾桂明
 */
public interface HiRefresh {
    /***
     * 刷新的时候时候禁止滚动
     * @param disableRefreshScroll 是否禁止滚动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /***
     * 设置下拉刷新的监听
     * @param hiRefreshListener
     */
    void setRefreshListener(HiRefreshListener hiRefreshListener);

    /***
     * 设置下拉刷新的头部
     * @param hiOverView
     */
    void setRefreshOverView(HiOverView hiOverView);

    interface HiRefreshListener {
        //下拉刷新的回调
        void onRefresh();
        //是否开启下拉刷新
        boolean enableRefresh();
    }
}

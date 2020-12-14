package com.example.hi_ui.ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/***
 * @date 2020年12月14日
 * @param <Tab>  底部的一个一个tab
 * @param <D>   tab 对应的数据
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {
    /***
     * 通过tabinfo来寻找 tab
     * @param data
     * @return
     */
    Tab findTab(@NonNull D data);

    /***
     * tab切换的时候的监听
     * @param listener
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    /***
     * 设置默认选中的tab
     * @param defaultInfo
     */
    void defaultSelected(@NonNull D defaultInfo);

    /***
     * 将一个个tab加载到底部的整个布局中
     * @param infoList  一个存放了tabinfo的list集合
     */
    void inflateInfo(@NonNull List<D> infoList);

    /***
     *
     * @param <D>
     */
    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, @Nullable D prevInfo, @NonNull D nextInfo);

    }
}

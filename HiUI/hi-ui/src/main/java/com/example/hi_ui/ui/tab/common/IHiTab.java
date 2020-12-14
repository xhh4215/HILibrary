package com.example.hi_ui.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/***
 * HiTab对外的通用接口
 * @param <D>
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {

    void setHiTabInfo(@NonNull D data);

    /***
     * 动态的修改某个item的大小
     * @param height
     */
    void resetHeight(@Px int height);
}

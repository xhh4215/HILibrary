package com.example.hi_ui.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/***
 * @date 2020年12月14日
 * @author 栾桂明
 * @desc HiTab对外的通用接口
 * @param <D> 这是一个设置Tab信息信息的数据
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    /***
     * 设置当前的Tab的详细内容信息
     * @param data
     */
    void setHiTabInfo(@NonNull D data);

    /***
     * 动态的修改某个item的大小
     * @param height
     */
    void resetHeight(@Px int height);
}

package com.example.hi_ui.ui.banner.core;

/***
 * HiBanner的数据绑定接口  基于这个接口可以实现数据的绑定和框架层的解耦
 */
public interface IBindAdapter {
    void  onBind(HiBannerAdapter.HiBannerViewHolder viewHolder,HiBannerMo mo,int position);
}

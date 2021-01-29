package com.example.asproj.arch.java;

/***
 * MVP 架构的基础View层   内部包含的是一些 界面通用的逻辑
 * 例如  加载数据的加载动画
 */
public interface BaseView {
    void showLoading();

    void hideLoading();
}

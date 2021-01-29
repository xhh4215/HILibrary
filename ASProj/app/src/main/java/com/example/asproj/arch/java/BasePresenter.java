package com.example.asproj.arch.java;

/***
 * MVP架构的P层此处实现和V层的绑定
 */
public class BasePresenter<IView extends BaseView> {
    public IView view;

    void attchView(IView view) {
        this.view = view;
    }

    void distatch() {
        this.view = null;
    }
}

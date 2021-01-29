package com.example.hi_arch.arch.mvp.java;

/***
 * MVP架构的P层此处实现和V层的绑定
 */
public class IMvpPresenterInJava<IView extends IMvpViewInJava> {
    public IView view;

    void attchView(IView view) {
        this.view = view;
    }

    void distatch() {
        this.view = null;
    }
}

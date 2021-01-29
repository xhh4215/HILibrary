package com.example.hiarch.java_mvp;


import com.example.hi_arch.arch.mvp.java.IMvpPresenterInJava;
import com.example.hi_arch.arch.mvp.java.IMvpViewInJava;

public interface MvpInJavaContract {

    interface View extends IMvpViewInJava {
        void onCommonSuccess(String value);

        void onCommonFail(String error);
    }


    abstract class Presenter extends IMvpPresenterInJava<View> {
        abstract void HttpCommon(String param);

    }


}

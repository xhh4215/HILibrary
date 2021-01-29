package com.example.asproj.arch.java;


import com.example.asproj.arch.java.BasePresenter;
import com.example.asproj.arch.java.BaseView;

public interface CommonContract {

    interface View extends BaseView {
        void onCommonSuccess(String value);


        void onCommonFail(String error);
    }


    abstract class Presenter extends BasePresenter<View> {
        abstract void HttpCommon(String param);

    }


}

package com.example.asproj.arch.java;

import com.example.asproj.arch.java.CommonContract;

public class CommonPresenterImp extends CommonContract.Presenter {
    @Override
    void HttpCommon(String param) {
        if (true) {
            view.onCommonSuccess("success");
        } else {
            view.onCommonFail("fail");
        }
    }
}

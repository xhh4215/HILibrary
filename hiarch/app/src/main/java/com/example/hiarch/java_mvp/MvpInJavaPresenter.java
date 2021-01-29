package com.example.hiarch.java_mvp;


import android.text.TextUtils;
import android.util.Log;

public class MvpInJavaPresenter extends MvpInJavaContract.Presenter {
    @Override
    public void HttpCommon(String param) {
        Log.d("tag", "网络请求中....");
        if (!TextUtils.isEmpty(param)) {
            view.onCommonSuccess("请求成功");
        } else {
            view.onCommonFail("请求失败");
        }
    }
}

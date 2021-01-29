package com.example.hiarch.java_mvp;


import android.os.Bundle;
import android.util.Log;

import com.example.hi_arch.arch.mvp.java.BaseActivity;
import com.example.hiarch.R;

public class MvpInJavaActivity extends BaseActivity<MvpInJavaPresenter> implements MvpInJavaContract.View {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_in_java);
        mPresenter.HttpCommon("发起了网络请求");
    }


    @Override
    public void onCommonSuccess(String value) {
        Log.d("tag", value);
    }

    @Override
    public void onCommonFail(String error) {
        Log.d("tag", error);
    }
}
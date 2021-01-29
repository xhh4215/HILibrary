package com.example.hi_arch.arch.mvp.java;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
 

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseActivity<P extends IMvpPresenterInJava> extends AppCompatActivity implements IMvpViewInJava {
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        // 判断当前的是不是具备范型参数
        if (genericSuperclass instanceof ParameterizedType) {
            //获取范型参数的集合
            Type[] params = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (params != null && params[0] instanceof IMvpPresenterInJava) {
                try {
                    mPresenter = (P) params[0].getClass().newInstance();
                    mPresenter.attchView(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.distatch();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}

package com.example.asproj;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.asproj.http.ApiFactory;
import com.example.asproj.http.TestApi;
import com.example.asproj.logic.MainActivityLogic;
import com.example.asproj.logic.MainActivityLogic.ActivityProvider;
import com.example.common.manager.ActivityManager;
import com.example.common.ui.component.HiBaseActivity;
import com.example.hi_library.restful.HiResponse;
import com.example.hi_library.restful.callback.HiCallBack;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends HiBaseActivity implements ActivityProvider {
    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.Companion.getInstance().addFrontBackCallback(front -> Toast.makeText(MainActivity.this, "前后天切换", Toast.LENGTH_SHORT).show());
        activityLogic = new MainActivityLogic(this, savedInstanceState);
        ApiFactory.INSTANCE.create(TestApi.class).listCities("immoc").enqueue(new HiCallBack<JsonObject>() {
            @Override
            public void onSuccess(@NotNull HiResponse<JsonObject> response) {

            }

            @Override
            public void onFailed(@NotNull Throwable throwable) {

            }
        });
     }

    /***
     * 执行的时机
     * 1 用户按了返回键
     * 2 切换最近应用的时候
     * 3 按下电源键关闭屏幕的时候
     * 4 屏幕发生旋转的时候
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }



}